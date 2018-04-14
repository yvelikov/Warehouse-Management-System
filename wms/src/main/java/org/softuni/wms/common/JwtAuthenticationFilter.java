package org.softuni.wms.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.softuni.wms.areas.users.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final int EXPIRATION_DURATION = 1_200_000;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try{
            ServletInputStream inputStream = request.getInputStream();
            User user = new ObjectMapper().readValue(inputStream, User.class);
//            Map<String, String[]> parameterMap = request.getParameterMap();
//            User user = new User();
//            user.setUsername(parameterMap.get("username")[0]);
//            user.setPassword(parameterMap.get("password")[0]);
            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>()
                    ));
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((User)authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_DURATION))
                .signWith(SignatureAlgorithm.HS256,JwtSecurityConstants.SECRET.getBytes())
                .compact();

        response.getWriter().append("{\"Authorization\": \"Bearer " + token + "\"}");
        response.setContentType("application/json");
//        response.addHeader(JwtSecurityConstants.TOKEN_HEADER,JwtSecurityConstants.TOKEN_PREFIX + token);
    }
}

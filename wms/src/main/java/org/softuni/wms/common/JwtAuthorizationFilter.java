package org.softuni.wms.common;

import io.jsonwebtoken.Jwts;
import org.softuni.wms.areas.users.services.api.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtSecurityConstants.TOKEN_HEADER);

        if(header == null || !header.startsWith(JwtSecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = this.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String header = request.getHeader(JwtSecurityConstants.TOKEN_HEADER);
        String user = Jwts.parser()
                .setSigningKey(JwtSecurityConstants.SECRET.getBytes())
                .parseClaimsJws(header.replace(JwtSecurityConstants.TOKEN_PREFIX,""))
                .getBody()
                .getSubject();

        if(user != null){
            return new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    this.userService.loadUserByUsername(user).getAuthorities());
        }

        return null;
    }
}

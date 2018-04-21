package org.softuni.wms.interceptors;

import org.softuni.wms.annotations.PreAuthenticateAction;
import org.softuni.wms.areas.users.entities.enums.UserRole;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;

@Component
public class UserAuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    @Autowired
    public UserAuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (!handlerMethod.getMethod().isAnnotationPresent(PreAuthenticateAction.class)) {
                return super.preHandle(request, response, handler);
            }

            if (handlerMethod.getMethodAnnotation(PreAuthenticateAction.class).inRole().equals(UserRole.ADMIN.name())) {

                Principal principal = request.getUserPrincipal();
                UserDetails userDetails = this.userService.loadUserByUsername(principal.getName());
                StringBuffer requestURL = request.getRequestURL();
                String userId = requestURL.substring(requestURL.lastIndexOf("/") + 1);
                UserEditDto userEditDto = this.userService.findById(userId);

                if (userEditDto.getAuthorities().contains(UserRole.SUPER_ADMIN.name()) && this.isAdmin(userDetails)) {
                    throw new AccessDeniedException(HttpStatus.FORBIDDEN.getReasonPhrase());
                }
            } else return true;
        }
        return true;
    }

    private boolean isAdmin(UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(UserRole.ADMIN.name())) {
                return true;
            }
        }
        return false;
    }
}

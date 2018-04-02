package org.softuni.wms.interseptors;

import org.softuni.wms.annotations.FirstUserOnly;
import org.softuni.wms.areas.users.services.RoleService;
import org.softuni.wms.areas.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FirstUserInterceptor extends HandlerInterceptorAdapter{

    private final UserService userService;

    @Autowired
    public FirstUserInterceptor(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (!handlerMethod.getMethod().isAnnotationPresent(FirstUserOnly.class)) {
                return super.preHandle(request, response, handler);
            }

            if(handlerMethod.getMethodAnnotation(FirstUserOnly.class).value()){
                if(this.userService.getUsersCount() == 0){
                    return true;
                }

                response.sendRedirect("/");
                return false;
            }
        }

        return super.preHandle(request, response, handler);
    }
}

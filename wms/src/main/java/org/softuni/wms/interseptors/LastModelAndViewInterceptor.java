package org.softuni.wms.interseptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LastModelAndViewInterceptor extends HandlerInterceptorAdapter{

    public static final String LAST_MODEL_VIEW_ATTRIBUTE = LastModelAndViewInterceptor.class.getName() + ".lastModelAndView";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.getSession(true).setAttribute(LAST_MODEL_VIEW_ATTRIBUTE, modelAndView);
        super.postHandle(request, response, handler, modelAndView);
    }
}

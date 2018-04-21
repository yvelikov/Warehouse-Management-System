package org.softuni.wms.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class FormInterceptor extends HandlerInterceptorAdapter {

    private static final String FORM_MODE = "formMode";
    private static final String EDIT = "edit";
    private static final String DISABLE = "disable";
    private static final String ENABLE = "enable";
    private static final String DELIVERY_NOTES = "delivery_notes";
    private static final String ISSUE_NOTES = "issue_notes";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(GetMapping.class)) {
                GetMapping annotation = handlerMethod.getMethodAnnotation(GetMapping.class);
                this.assignFormMode(modelAndView, annotation.path());
            } else if (handlerMethod.hasMethodAnnotation(PostMapping.class)) {
                PostMapping annotation = handlerMethod.getMethodAnnotation(PostMapping.class);
                this.assignFormMode(modelAndView, annotation.path());
            }
        }
    }

    private void assignFormMode(ModelAndView modelAndView, String[] path) {
        if (Arrays.stream(path).anyMatch(x -> x.contains(EDIT))) {
            modelAndView.addObject(FORM_MODE, EDIT);
        } else if (Arrays.stream(path).anyMatch(x -> x.contains(DISABLE))) {
            modelAndView.addObject(FORM_MODE, DISABLE);
        } else if (Arrays.stream(path).anyMatch(x -> x.contains(ENABLE))) {
            modelAndView.addObject(FORM_MODE, ENABLE);
        } else if (Arrays.stream(path).anyMatch(x -> x.contains(DELIVERY_NOTES))) {
            modelAndView.addObject(FORM_MODE, DELIVERY_NOTES);
        } else if (Arrays.stream(path).anyMatch(x -> x.contains(ISSUE_NOTES))) {
            modelAndView.addObject(FORM_MODE, ISSUE_NOTES);
        }
    }
}

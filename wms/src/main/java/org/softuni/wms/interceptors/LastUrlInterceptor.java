package org.softuni.wms.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class LastUrlInterceptor extends HandlerInterceptorAdapter{

    public static final String LAST_VISITED_PAGES = "lastVisitedPages";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(handler instanceof HandlerMethod){
            if(null == request.getSession(true).getAttribute(LAST_VISITED_PAGES )){
                ArrayDeque<String> views = new ArrayDeque<>();
                request.getSession(true).setAttribute(LAST_VISITED_PAGES, views);
            }
            Deque<String> lastViews = (ArrayDeque<String>) request.getSession(true).getAttribute(LAST_VISITED_PAGES);
            lastViews.push(request.getRequestURI());
        }

        super.postHandle(request, response, handler, modelAndView);
    }
}

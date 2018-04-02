package org.softuni.wms.controllers;

import org.softuni.wms.interseptors.LastUrlInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseController {
    protected ModelAndView view(String view) {
        return new ModelAndView(view);
    }

    protected ModelAndView view(String view, String name, Object model) {
        ModelAndView modelAndView = new ModelAndView(view);

        modelAndView.addObject(name, model);

        return modelAndView;
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

    protected ModelAndView redirectToLast(HttpServletRequest request) {
        Deque visitedPages = (ArrayDeque) request.getSession().getAttribute(LastUrlInterceptor.LAST_VISITED_PAGES);
        visitedPages.pop();
        String lastUrl = (String) visitedPages.pop();
        return this.redirect(lastUrl.equals("/login") ? "/" : lastUrl);
    }
}

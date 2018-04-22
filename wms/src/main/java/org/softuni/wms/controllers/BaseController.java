package org.softuni.wms.controllers;

import org.softuni.wms.interceptors.LastUrlInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public abstract class BaseController {

    private static List<String> routesToSkip = new ArrayList<String>(){{
        add("add");
        add("disable");
        add("enable");
        add("create");
        add("edit");
        add("deliver");
        add("issue");
        add("search");
        add("documents/partner");
    }};

    private boolean containsSkipRoute(String lastUrl) {
        for (String route : routesToSkip) {
            if(lastUrl.contains(route)){
                return true;
            }
        }
        return false;
    }

    protected ModelAndView view(String view) {
        return new ModelAndView(view);
    }

    protected ModelAndView view(String view, String name, Object model) {
        ModelAndView modelAndView = new ModelAndView(view);

        modelAndView.addObject(name, model);

        return modelAndView;
    }

    protected ModelAndView view(String view, Map<String, Object> modelMap) {
        ModelAndView modelAndView = new ModelAndView(view);
        for (Map.Entry<String, Object> modelEntry : modelMap.entrySet()) {
            modelAndView.addObject(modelEntry.getKey(), modelEntry.getValue());
        }
        return modelAndView;
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

    protected ModelAndView redirectToLast(HttpServletRequest request) {
        Deque visitedPages = (ArrayDeque) request.getSession().getAttribute(LastUrlInterceptor.LAST_VISITED_PAGES);
        visitedPages.pop();
        String lastUrl = (String) visitedPages.pop();

        while (true) {
            if (containsSkipRoute(lastUrl)) {
                lastUrl = (String) visitedPages.pop();
            } else {
                break;
            }
        }
        return this.redirect(lastUrl.equals("/login") ? "/" : lastUrl);
    }
}

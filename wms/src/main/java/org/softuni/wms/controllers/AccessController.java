package org.softuni.wms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController {

    @GetMapping("/unauthorized")
    public ModelAndView unauthorized(ModelAndView modelAndView){
        modelAndView.setViewName("unauthorized");
        return modelAndView;
    }
}

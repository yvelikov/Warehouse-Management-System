package org.softuni.wms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController extends BaseController{

    @GetMapping("/customers")
    public ModelAndView customers(){
        return this.view("customers");
    }

}

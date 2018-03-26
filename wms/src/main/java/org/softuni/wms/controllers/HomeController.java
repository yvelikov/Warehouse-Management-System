package org.softuni.wms.controllers;

import org.softuni.wms.services.api.RoleService;
import org.softuni.wms.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public HomeController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        if (this.userService.getUsersCount() == 0) {
            modelAndView.addObject("isFirstUser", true);
            this.roleService.seedRoles();
        }
        return modelAndView;
    }
}

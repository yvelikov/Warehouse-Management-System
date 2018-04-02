package org.softuni.wms.areas.home.controllers;

import org.softuni.wms.areas.users.services.RoleService;
import org.softuni.wms.areas.users.services.UserService;
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

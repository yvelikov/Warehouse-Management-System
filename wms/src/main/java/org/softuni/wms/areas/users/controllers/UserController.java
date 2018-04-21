package org.softuni.wms.areas.users.controllers;

import org.softuni.wms.annotations.FirstUserOnly;
import org.softuni.wms.areas.users.models.binding.RegisterUserDto;
import org.softuni.wms.areas.users.services.api.UserService;
import org.softuni.wms.constants.Constants;
import org.softuni.wms.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error,
                              @RequestParam(required = false, name = "logout") String logout) {
        if (error != null) {
            return this.view("login","error", Constants.INVALID_CREDENTIALS);
        }

        if (logout != null) {
            return this.view("login","logout", Constants.LOGOUT_SUCCESSFUL);
        }

        return this.view("login");
    }

    @FirstUserOnly
    @GetMapping("/register")
    public ModelAndView register() {
        return this.view("register","registerUserDto", new RegisterUserDto());
    }

    @FirstUserOnly
    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute RegisterUserDto registerUserDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.view("register", "registerUserDto", registerUserDto);
        }
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", Constants.PASSWORD_ERROR);
            return this.view("register", "registerUserDto", registerUserDto);
        }

        this.userService.registerFirstUser(registerUserDto);

        return this.redirect("/login");
    }
}

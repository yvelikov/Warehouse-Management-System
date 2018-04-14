package org.softuni.wms.areas.users.controllers;

import org.softuni.wms.annotations.FirstUserOnly;
import org.softuni.wms.areas.users.models.binding.RegisterUserDto;
import org.softuni.wms.areas.users.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final String INVALID_CREDENTIALS = "Invalid username or password";
    private static final String LOGOUT_SUCCESSFUL = "Logged out successfully!";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error,
                              @RequestParam(required = false, name = "logout") String logout,
                              ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        if (error != null) {
            error = INVALID_CREDENTIALS;
            modelAndView.addObject("error", error);
        }

        if (logout != null) {
            logout = LOGOUT_SUCCESSFUL;
            modelAndView.addObject("logout", logout);
        }

        return modelAndView;
    }

    @FirstUserOnly
    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("registerUserDto", new RegisterUserDto());
        return modelAndView;
    }

    @FirstUserOnly
    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute RegisterUserDto registerUserDto,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerUserDto", registerUserDto);
            return "register";
        }
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords must match");
            return "register";
        }
        this.userService.registerFirstUser(registerUserDto);

        return "redirect:/login";
    }
}

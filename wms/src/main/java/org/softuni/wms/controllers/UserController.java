package org.softuni.wms.controllers;

import org.softuni.wms.annotations.FirstUserOnly;
import org.softuni.wms.models.binding.RegisterUserDto;
import org.softuni.wms.services.api.RoleService;
import org.softuni.wms.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error,
                              @RequestParam(required = false, name = "logout") String logout,
                              ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        if (error != null) {
            error = "Invalid username or password";
            modelAndView.addObject("error", error);
        }

        if (logout != null) {
            logout = "Logged out successfully!";
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

//    @GetMapping("/login")
//    public ModelAndView login(ModelAndView modelAndView) {
//
//        modelAndView.setViewName("login");
//        modelAndView.addObject("userLoginDto", new UserLoginDto());
//        return modelAndView;
//    }
//
//    @PostMapping("/login")
//    public String loginConfirm(@ModelAttribute UserLoginDto userLoginDto, Model model, HttpSession httpSession) {
//        return "redirect:/";
//    }


//    @GetMapping("/users/create")
//    public ModelAndView register(ModelAndView modelAndView) {
//        modelAndView.setViewName("users/create-user");
//
//        List<RoleDto> roles = this.roleService.findAll();
//
//        modelAndView.addObject("roles", roles);
//        modelAndView.addObject("userDto", new UserDto());
//        return modelAndView;
//    }

//    @PostMapping("/users/create")
//    public String registerConfirm(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, Model model) {
//
//        List<RoleDto> roles = null;
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("userDto", userDto);
//            roles = this.roleService.findAll();
//            model.addAttribute("roles", roles);
//            return "users/create-user";
//        }
//
//        if(!userDto.getPassword().equals(userDto.getConfirmPassword())){
//            bindingResult.rejectValue("confirmPassword","error.confirmPassword", "Passwords must match");
//            roles = this.roleService.findAll();
//            model.addAttribute("roles", roles);
//            return "users/create-user";
//        }
//        this.userService.register(userDto);
//
//        return "redirect:/users/all";
//    }

}

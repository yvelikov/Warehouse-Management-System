package org.softuni.wms.controllers;

import org.softuni.wms.models.binding.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView){
        modelAndView.setViewName("register");
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            System.out.println();
        }
        return "redirect:/";
    }
}

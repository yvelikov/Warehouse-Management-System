package org.softuni.wms.controllers;

import org.softuni.wms.interseptors.LastModelAndViewInterceptor;
import org.softuni.wms.models.binding.UserDto;
import org.softuni.wms.models.binding.UserEditDto;
import org.softuni.wms.models.service.RoleDto;
import org.softuni.wms.models.view.AllRolesViewDto;
import org.softuni.wms.models.view.UserViewDto;
import org.softuni.wms.services.api.RoleService;
import org.softuni.wms.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users/create")
    public ModelAndView register(ModelAndView modelAndView) {

        modelAndView.setViewName("admin/users/create-user");
        List<RoleDto> roles = this.roleService.findAll();
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }

    @PostMapping("/users/create")
    public ModelAndView registerConfirm(@RequestParam String action,
                                        @Valid @ModelAttribute UserDto userDto,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView,
                                        HttpServletRequest request) {

        if ("create".equals(action)) {
            List<RoleDto> roles = null;

            if (bindingResult.hasErrors()) {
                modelAndView.setViewName("admin/users/create-user");
                modelAndView.addObject("userDto", userDto);
                roles = this.roleService.findAll();
                modelAndView.addObject("roles", roles);
                return modelAndView;
            }

            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                modelAndView.setViewName("admin/users/create-user");
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords must match");
                roles = this.roleService.findAll();
                modelAndView.addObject("roles", roles);
                return modelAndView;
            }
            this.userService.register(userDto);
        }

        if ("cancel".equals(action)) {
            return (ModelAndView) request.getSession().getAttribute(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
        }

        modelAndView.setViewName("redirect:/admin/users/all");
        return modelAndView;
    }


    @GetMapping("/users/all")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/users/all-users");
        List<UserViewDto> allUsers = this.userService.findAllSortedByUsername();
        modelAndView.addObject("allUsers", allUsers);
        return modelAndView;
    }

    @GetMapping("/users/edit/{id}")
    public ModelAndView editUser(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.setViewName("admin/users/edit-user");
        List<RoleDto> roles = this.roleService.findAll();
        AllRolesViewDto allRoles = new AllRolesViewDto();

        for (RoleDto role : roles) {
            allRoles.add(role.getAuthority());
        }
        UserEditDto userEditDto = this.userService.findById(id);
        modelAndView.addObject("allRoles", allRoles);
        modelAndView.addObject("userEditDto", userEditDto);

        return modelAndView;
    }

    @PostMapping("/users/edit/{id}")
    public ModelAndView editUserConfirm(@RequestParam String action,
                                        @PathVariable String id,
                                        @Valid @ModelAttribute UserEditDto userEditDto,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView,
                                        HttpServletRequest request) {
        if ("edit".equals(action)) {
            if (bindingResult.hasErrors()) {
                modelAndView.setViewName("admin/users/edit-user");
                List<RoleDto> roles = this.roleService.findAll();
                AllRolesViewDto allRoles = new AllRolesViewDto();

                for (RoleDto role : roles) {
                    allRoles.add(role.getAuthority());
                }

                modelAndView.addObject("allRoles", allRoles);
                modelAndView.addObject("userEditDto", userEditDto);
                return modelAndView;
            }

            this.userService.edit(userEditDto);
        }

        if ("cancel".equals(action)) {
            return (ModelAndView) request.getSession().getAttribute(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
        }

        modelAndView.setViewName("redirect:/admin/users/all");

        return modelAndView;
    }
}

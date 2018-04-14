package org.softuni.wms.areas.users.controllers;

import org.softuni.wms.annotations.PreAuthenticateAction;
import org.softuni.wms.areas.users.entities.enums.UserRole;
import org.softuni.wms.areas.users.models.binding.UserDto;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.softuni.wms.areas.users.models.view.AllRolesViewDto;
import org.softuni.wms.areas.users.models.view.UserViewDto;
import org.softuni.wms.areas.users.services.api.RoleService;
import org.softuni.wms.areas.users.services.api.UserService;
import org.softuni.wms.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private static final String ACTION_RESULT_MESSAGE = "User \'%s\' successfully %s";

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private ModelAndView getUserView(UserEditDto userEditDto, ModelAndView modelAndView) {
        String debug = "";
        modelAndView.setViewName("admin/users/edit-user");
        AllRolesViewDto allRoles = this.roleService.getAllRolesView();
        modelAndView.addObject("allRoles", allRoles);
        modelAndView.addObject("userEditDto", userEditDto);

        return modelAndView;
    }

    @GetMapping("/users/create")
    public ModelAndView register(ModelAndView modelAndView) {

        String role = UserRole.SUPER_ADMIN.name();

        modelAndView.setViewName("admin/users/create-user");
        List<RoleServiceDto> roles = this.roleService.findAll();
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
            List<RoleServiceDto> roles = null;

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
            return this.redirectToLast(request);
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

    @PreAuthenticateAction(inRole = "ADMIN")
    @GetMapping("/users/edit/{id}")
    public ModelAndView editUser(@PathVariable String id, ModelAndView modelAndView) {
        return getUserView(this.userService.findById(id), modelAndView);
    }


    @PreAuthenticateAction(inRole = "ADMIN")
    @PostMapping("/users/edit/{id}")
    public ModelAndView editUserConfirm(@RequestParam String action,
                                        @PathVariable String id,
                                        @Valid @ModelAttribute UserEditDto userEditDto,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes) {
        if ("edit".equals(action)) {
            if (bindingResult.hasErrors()) {
                return this.getUserView(userEditDto, modelAndView);
            }
            String actionResult = String.format(ACTION_RESULT_MESSAGE, userEditDto.getUsername(), action + "ed");
            redirectAttributes.addFlashAttribute("actionResult", actionResult);
            this.userService.edit(userEditDto);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        modelAndView.setViewName("redirect:/admin/users/all");

        return modelAndView;
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @GetMapping("/users/disable/{id}")
    public ModelAndView disableUser(@PathVariable String id, ModelAndView modelAndView) {
        return getUserView(this.userService.findById(id), modelAndView);
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @PostMapping("/users/disable/{id}")
    public ModelAndView disableUserConfirm(@RequestParam String action,
                                          @PathVariable String id,
                                          @Valid @ModelAttribute UserEditDto userEditDto,
                                          BindingResult bindingResult,
                                          ModelAndView modelAndView,
                                          HttpServletRequest request,
                                          RedirectAttributes redirectAttributes) {
        if ("disable".equals(action)) {
            String actionResult = String.format(ACTION_RESULT_MESSAGE, userEditDto.getUsername(), "disabled");
            redirectAttributes.addFlashAttribute("actionResult", actionResult);
            this.userService.disable(userEditDto.getId());
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        modelAndView.setViewName("redirect:/admin/users/all");

        return modelAndView;
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @GetMapping("/users/enable/{id}")
    public ModelAndView enableUser(@PathVariable String id, ModelAndView modelAndView) {
        return getUserView(this.userService.findById(id), modelAndView);
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @PostMapping("/users/enable/{id}")
    public ModelAndView enableUserConfirm(@RequestParam String action,
                                           @PathVariable String id,
                                           @Valid @ModelAttribute UserEditDto userEditDto,
                                           BindingResult bindingResult,
                                           ModelAndView modelAndView,
                                           HttpServletRequest request,
                                           RedirectAttributes redirectAttributes) {
        if ("enable".equals(action)) {
            String actionResult = String.format(ACTION_RESULT_MESSAGE, userEditDto.getUsername(), "enabled");
            redirectAttributes.addFlashAttribute("actionResult", actionResult);
            this.userService.enable(userEditDto.getId());
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        modelAndView.setViewName("redirect:/admin/users/all");

        return modelAndView;
    }

}

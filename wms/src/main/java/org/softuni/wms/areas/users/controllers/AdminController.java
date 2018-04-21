package org.softuni.wms.areas.users.controllers;

import org.softuni.wms.annotations.PreAuthenticateAction;
import org.softuni.wms.areas.users.models.binding.UserDto;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.softuni.wms.areas.users.models.view.AllRolesViewDto;
import org.softuni.wms.areas.users.models.view.UserViewDto;
import org.softuni.wms.areas.users.services.api.RoleService;
import org.softuni.wms.areas.users.services.api.UserService;
import org.softuni.wms.constants.Constants;
import org.softuni.wms.controllers.BaseController;
import org.softuni.wms.mail.EmailService;
import org.softuni.wms.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
@RequestMapping("/admin")
public class AdminController extends BaseController {


    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, EmailService emailService) {
        this.userService = userService;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    private ModelAndView getUserView(UserEditDto userEditDto) {
        AllRolesViewDto allRoles = this.roleService.getAllRolesView();
        return this.view("admin/users/edit-user", new HashMap<>() {{
            put("allRoles", allRoles);
            put("userEditDto", userEditDto);
        }});
    }

    private void sendConfirmationEmail(UserDto userDto) {
        Mail mail = new Mail();
        mail.setFrom(Constants.APPLICATION_EMAIL);
        mail.setTo(userDto.getEmail());
        mail.setSubject(Constants.EMAIL_SUBJECT);
        mail.setContent(String.format(Constants.EMAIL_CONTENT,
                userDto.getUsername(), userDto.getPassword()));

        this.emailService.sendSimpleMessage(mail);
    }

    private boolean isValidUser(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {
        boolean hasErrors = false;

        if (bindingResult.hasErrors()) {
            hasErrors = true;
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", Constants.PASSWORD_ERROR);
            hasErrors = true;
        }

        if (this.userService.findByUsername(userDto.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", Constants.USERNAME_ERROR);
            hasErrors = true;
        }

        if (this.userService.findUserByEmail(userDto.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", Constants.EMAIL_ERROR);
            hasErrors = true;
        }
        return hasErrors;
    }

    @GetMapping("/users/create")
    public ModelAndView register(ModelAndView modelAndView) {
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
                                        HttpServletRequest request) {

        if ("create".equals(action)) {
            if (this.isValidUser(userDto, bindingResult)) {
                List<RoleServiceDto> roles = this.roleService.findAll();
                ;
                return this.view("admin/users/create-user", new HashMap<>() {{
                    put("userDto", userDto);
                    put("roles", roles);
                }});
            }

            boolean result = this.userService.addUser(userDto);
            if (result) this.sendConfirmationEmail(userDto);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }
        return redirect("/admin/users/all");
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
    public ModelAndView editUser(@PathVariable String id) {
        return this.getUserView(this.userService.findById(id));
    }


    @PreAuthenticateAction(inRole = "ADMIN")
    @PostMapping("/users/edit/{id}")
    public ModelAndView editUserConfirm(@RequestParam String action,
                                        @PathVariable String id,
                                        @Valid @ModelAttribute UserEditDto userEditDto,
                                        BindingResult bindingResult,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes) {
        if ("edit".equals(action)) {
            if (bindingResult.hasErrors()) {
                return this.getUserView(userEditDto);
            }

            String actionResult = this.userService.edit(userEditDto)
                    ? String.format(Constants.ACTION_RESULT_MESSAGE, userEditDto.getUsername(), "edited")
                    : Constants.ACTION_RESULT_NOT_COMPLETED;
            redirectAttributes.addFlashAttribute("actionResult", actionResult);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        return this.redirect("/admin/users/all");
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @GetMapping("/users/disable/{id}")
    public ModelAndView disableUser(@PathVariable String id) {
        return this.getUserView(this.userService.findById(id));
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @PostMapping("/users/disable/{id}")
    public ModelAndView disableUserConfirm(@RequestParam String action,
                                           @PathVariable String id,
                                           @Valid @ModelAttribute UserEditDto userEditDto,
                                           BindingResult bindingResult,
                                           HttpServletRequest request,
                                           RedirectAttributes redirectAttributes) {
        if ("disable".equals(action)) {
            String actionResult = this.userService.disable(userEditDto.getId())
                    ? String.format(Constants.ACTION_RESULT_MESSAGE, userEditDto.getUsername(), "disabled")
                    : Constants.ACTION_RESULT_NOT_COMPLETED;
            redirectAttributes.addFlashAttribute("actionResult", actionResult);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        return this.redirect("/admin/users/all");
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @GetMapping("/users/enable/{id}")
    public ModelAndView enableUser(@PathVariable String id) {
        return this.getUserView(this.userService.findById(id));
    }

    @PreAuthenticateAction(inRole = "ADMIN")
    @PostMapping("/users/enable/{id}")
    public ModelAndView enableUserConfirm(@RequestParam String action,
                                          @PathVariable String id,
                                          @Valid @ModelAttribute UserEditDto userEditDto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request,
                                          RedirectAttributes redirectAttributes) {
        if ("enable".equals(action)) {
            String actionResult = this.userService.enable(userEditDto.getId())
                    ? String.format(Constants.ACTION_RESULT_MESSAGE, userEditDto.getUsername(), "enabled")
                    : Constants.ACTION_RESULT_NOT_COMPLETED;
            redirectAttributes.addFlashAttribute("actionResult", actionResult);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        return this.redirect("/admin/users/all");
    }
}

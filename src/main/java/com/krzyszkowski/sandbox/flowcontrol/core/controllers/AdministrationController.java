package com.krzyszkowski.sandbox.flowcontrol.core.controllers;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDto;
import com.krzyszkowski.sandbox.flowcontrol.core.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("administration")
public class AdministrationController {

    private final UserService userService;

    public AdministrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String administration() {
        return "administration";
    }

    @GetMapping("users")
    public String users(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                        Model model) {
        model.addAttribute("users", userService.getPagedUsers(page - 1, size));
        return "administration/users";
    }

    @GetMapping("users/add")
    public String add(Model model) {
        model.addAttribute("roles", User.Role.values());
        model.addAttribute("user", new UserDto());
        return "administration/users-add";
    }

    @PostMapping("users/add")
    public ModelAndView add(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("administration/users-add");
            response.addObject("roles", User.Role.values());
            response.addObject("user", userDto);
            return response;
        }

        userService.addUser(userDto);

        return new ModelAndView("redirect:/administration/users/add?success");
    }

    @GetMapping("users/delete/{id}")
    public String delete(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/administration/users";
    }
}

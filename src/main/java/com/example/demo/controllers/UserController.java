package com.example.demo.controllers;

import com.example.demo.models.Entrance;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findActive());
        return "user/main";
    }

    @GetMapping("/add")
    public String userAdd(User user, Model model) {
        model.addAttribute("roles", Role.values());
        return "user/add";
    }

    @PostMapping("/add")
    public String userAddSave(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "user/add";
        }
        user.setActive(true);
        userRepository.save(user);
        return "redirect:";
    }

    @GetMapping("/edit/{user}")
    public String userEdit(User user, Model model) {
        model.addAttribute("roles", Role.values());
        return "user/edit";
    }

    @PostMapping("/edit/{user}")
    public String userEditSave(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "user/edit";
        }
        userRepository.save(user);
        return "redirect:../";
    }

    @GetMapping("/del/{user}")
    @PreAuthorize("isAuthenticated()")
    public String userDel(
            User user) {
        user.setActive(false);
        userRepository.save(user);
        return "redirect:../";
    }
}
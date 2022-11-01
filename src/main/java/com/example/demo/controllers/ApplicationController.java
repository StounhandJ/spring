package com.example.demo.controllers;

import com.example.demo.models.Application;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("application")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String applicationMain(Model model) {
        List<User> user = userRepository.findByRoles(Role.ADMIN);
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        return "application/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String applicationAdd(Application application, Model model) {
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        return "application/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String applicationPostAdd(
            @ModelAttribute("application") @Valid Application application,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
            model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
            return "application/add";
        }
        applicationRepository.save(application);
        return "redirect:";
    }

    @GetMapping("/edit/{application}")
    @PreAuthorize("isAuthenticated()")
    public String applicationEdit(Application application, Model model) {
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        model.addAttribute("appli", application);
        return "application/edit";
    }

    @PostMapping("/edit/{application}")
    @PreAuthorize("isAuthenticated()")
    public String applicationPostEdit(
            @ModelAttribute("application") @Valid Application application,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
            model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
            model.addAttribute("appli", application);
            return "application/edit";
        }
        applicationRepository.save(application);
        return "redirect:../";
    }

    @GetMapping("/show/{application}")
    public String applicationShow(
            Application application) {
        return "application/show";
    }

    @GetMapping("/del/{application}")
    @PreAuthorize("isAuthenticated()")
    public String applicationDel(
            Application application) {
        applicationRepository.delete(application);
        return "redirect:../";
    }
}

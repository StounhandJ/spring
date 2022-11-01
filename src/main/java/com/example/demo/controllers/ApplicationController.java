package com.example.demo.controllers;

import com.example.demo.models.Application;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("application")
@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String applicationMain(Model model) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRoles().contains(Role.CLIENT)) {
            model.addAttribute("applications", applicationRepository.findByClient_Id(user.getId()));
        } else {
            model.addAttribute("applications", applicationRepository.findAll());
        }

        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        return "application/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String applicationAdd(Application application, Model model) {
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        return "application/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String applicationEdit(Application application, Model model) {
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        model.addAttribute("appli", application);
        return "application/edit";
    }

    @PostMapping("/edit/{application}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
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
            Application application, Model model) {
        model.addAttribute("appli", application);
        return "application/show";
    }

    @GetMapping("/del/{application}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String applicationDel(
            Application application) {
        applicationRepository.delete(application);
        return "redirect:../";
    }
}

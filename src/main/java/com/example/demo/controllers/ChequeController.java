package com.example.demo.controllers;

import com.example.demo.models.Cheque;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.ChequeRepository;
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
@RequestMapping("cheque")
@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
public class ChequeController {
    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String chequeMain(Model model) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRoles().contains(Role.CLIENT)) {
            model.addAttribute("cheques", chequeRepository.findByApplicationClient_Id(user.getId()));
        } else {
            model.addAttribute("cheques", chequeRepository.findAll());
        }

        model.addAttribute("cheques", chequeRepository.findAll());
        model.addAttribute("applications", applicationRepository.findAll());
        return "cheque/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String chequeAdd(Cheque cheque, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        return "cheque/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String chequePostAdd(
            @ModelAttribute("cheque") @Valid Cheque cheque,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("applications", applicationRepository.findAll());
            return "cheque/add";
        }
        chequeRepository.save(cheque);
        return "redirect:";
    }

    @GetMapping("/edit/{cheque}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String chequeEdit(Cheque cheque, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        return "cheque/edit";
    }

    @PostMapping("/edit/{cheque}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String chequePostEdit(
            @ModelAttribute("cheque") @Valid Cheque cheque,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("applications", applicationRepository.findAll());
            return "cheque/edit";
        }
        chequeRepository.save(cheque);
        return "redirect:../";
    }

    @GetMapping("/show/{cheque}")
    public String chequeShow(
            Cheque cheque, Model model) {
        model.addAttribute("cheque", cheque);
        return "cheque/show";
    }

    @GetMapping("/del/{cheque}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String chequeDel(
            Cheque cheque) {
        chequeRepository.delete(cheque);
        return "redirect:../";
    }
}

package com.example.demo.controllers;

import com.example.demo.models.Cheque;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.ChequeRepository;
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

@Controller
@RequestMapping("cheque")
public class ChequeController {
    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping()
    public String chequeMain(Model model) {
        model.addAttribute("cheques", chequeRepository.findAll());
        model.addAttribute("applications", applicationRepository.findAll());
        return "cheque/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String chequeAdd(Cheque cheque, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        return "cheque/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
    public String chequeEdit(Cheque cheque, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        return "cheque/edit";
    }

    @PostMapping("/edit/{cheque}")
    @PreAuthorize("isAuthenticated()")
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
            Cheque cheque) {
        return "cheque/show";
    }

    @GetMapping("/del/{cheque}")
    @PreAuthorize("isAuthenticated()")
    public String chequeDel(
            Cheque cheque) {
        chequeRepository.delete(cheque);
        return "redirect:../";
    }
}

package com.example.demo.controllers;

import com.example.demo.models.PaidTreatment;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.PaidTreatmentRepository;
import com.example.demo.repo.TypeTreatmentRepository;
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
@RequestMapping("paidTreatment")
public class PaidTreatmentController {
    @Autowired
    private PaidTreatmentRepository paidTreatmentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TypeTreatmentRepository typeTreatmentRepository;


    @GetMapping()
    public String paidTreatmentMain(Model model) {
        model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
        return "paidTreatment/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String paidTreatmentAdd(PaidTreatment paidTreatment, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
        return "paidTreatment/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String paidTreatmentPostAdd(
            @ModelAttribute("paidTreatment") @Valid PaidTreatment paidTreatment,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("applications", applicationRepository.findAll());
            model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
            return "paidTreatment/add";
        }
        paidTreatmentRepository.save(paidTreatment);
        return "redirect:";
    }

    @GetMapping("/edit/{paidTreatment}")
    @PreAuthorize("isAuthenticated()")
    public String paidTreatmentEdit(PaidTreatment paidTreatment, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
        return "paidTreatment/edit";
    }

    @PostMapping("/edit/{paidTreatment}")
    @PreAuthorize("isAuthenticated()")
    public String paidTreatmentPostEdit(
            @ModelAttribute("paidTreatment") @Valid PaidTreatment paidTreatment,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("applications", applicationRepository.findAll());
            model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
            return "paidTreatment/edit";
        }
        paidTreatmentRepository.save(paidTreatment);
        return "redirect:../";
    }

    @GetMapping("/show/{paidTreatment}")
    public String paidTreatmentShow(
            PaidTreatment paidTreatment) {
        return "paidTreatment/show";
    }

    @GetMapping("/del/{paidTreatment}")
    @PreAuthorize("isAuthenticated()")
    public String paidTreatmentDel(
            PaidTreatment paidTreatment) {
        paidTreatmentRepository.delete(paidTreatment);
        return "redirect:../";
    }
}

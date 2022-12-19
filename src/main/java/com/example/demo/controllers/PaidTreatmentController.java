package com.example.demo.controllers;

import com.example.demo.models.PaidTreatment;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.PaidTreatmentRepository;
import com.example.demo.repo.TypeTreatmentRepository;
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
@RequestMapping("paidTreatment")
@PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR', 'CLIENT', 'ACCOUNTANT')")
public class PaidTreatmentController {
    @Autowired
    private PaidTreatmentRepository paidTreatmentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TypeTreatmentRepository typeTreatmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String paidTreatmentMain(Model model) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRoles().contains(Role.CLIENT)) {
            model.addAttribute("paidTreatments", paidTreatmentRepository.findByApplicationClient_Id(user.getId()));
        } else {
            model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        }
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
        return "paidTreatment/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String paidTreatmentAdd(PaidTreatment paidTreatment, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
        return "paidTreatment/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String paidTreatmentEdit(PaidTreatment paidTreatment, Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        model.addAttribute("typeTreatments", typeTreatmentRepository.findAll());
        return "paidTreatment/edit";
    }

    @PostMapping("/edit/{paidTreatment}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
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
            PaidTreatment paidTreatment, Model model) {
        model.addAttribute("paidTreatment", paidTreatment);
        return "paidTreatment/show";
    }

    @GetMapping("/del/{paidTreatment}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String paidTreatmentDel(
            PaidTreatment paidTreatment) {
        paidTreatmentRepository.delete(paidTreatment);
        return "redirect:../";
    }
}

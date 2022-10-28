package com.example.demo.controllers;

import com.example.demo.models.MedicalPreparations;
import com.example.demo.repo.MedicalPreparationsRepository;
import com.example.demo.repo.ShelvingRepository;
import com.example.demo.repo.TypePreparationRepository;
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
@RequestMapping("medicalPreparations")
public class MedicalPreparationsController {
    @Autowired
    private MedicalPreparationsRepository medicalPreparationsRepository;

    @Autowired
    private ShelvingRepository shelvingRepository;

    @Autowired
    private TypePreparationRepository typePreparationRepository;

    @GetMapping()
    public String medicalPreparationsMain(Model model) {
        model.addAttribute("medicalPreparationss", medicalPreparationsRepository.findAll());
        model.addAttribute("shelving", shelvingRepository.findAll());
        model.addAttribute("typePreparations", typePreparationRepository.findAll());
        return "medicalPreparations/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String medicalPreparationsAdd(MedicalPreparations medicalPreparations, Model model) {
        model.addAttribute("shelving", shelvingRepository.findAll());
        model.addAttribute("typePreparations", typePreparationRepository.findAll());
        return "medicalPreparations/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String medicalPreparationsPostAdd(
            @ModelAttribute("medicalPreparations") @Valid MedicalPreparations medicalPreparations,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shelving", shelvingRepository.findAll());
            model.addAttribute("typePreparations", typePreparationRepository.findAll());
            return "medicalPreparations/add";
        }
        medicalPreparationsRepository.save(medicalPreparations);
        return "redirect:";
    }

    @GetMapping("/edit/{medicalPreparations}")
    @PreAuthorize("isAuthenticated()")
    public String medicalPreparationsEdit(MedicalPreparations medicalPreparations, Model model) {
        model.addAttribute("shelving", shelvingRepository.findAll());
        model.addAttribute("typePreparations", typePreparationRepository.findAll());
        return "medicalPreparations/edit";
    }

    @PostMapping("/edit/{medicalPreparations}")
    @PreAuthorize("isAuthenticated()")
    public String medicalPreparationsPostEdit(
            @ModelAttribute("medicalPreparations") @Valid MedicalPreparations medicalPreparations,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shelving", shelvingRepository.findAll());
            model.addAttribute("typePreparations", typePreparationRepository.findAll());
            return "medicalPreparations/edit";
        }
        medicalPreparationsRepository.save(medicalPreparations);
        return "redirect:../";
    }

    @GetMapping("/show/{medicalPreparations}")
    public String medicalPreparationsShow(
            MedicalPreparations medicalPreparations) {
        return "medicalPreparations/show";
    }

    @GetMapping("/del/{medicalPreparations}")
    @PreAuthorize("isAuthenticated()")
    public String medicalPreparationsDel(
            MedicalPreparations medicalPreparations) {
        medicalPreparationsRepository.delete(medicalPreparations);
        return "redirect:../";
    }
}

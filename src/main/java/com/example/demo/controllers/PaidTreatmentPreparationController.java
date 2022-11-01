package com.example.demo.controllers;

import com.example.demo.models.PaidTreatment;
import com.example.demo.models.PaidTreatmentPreparation;
import com.example.demo.models.keys.PaidTreatmentPreparationKey;
import com.example.demo.repo.MedicalPreparationsRepository;
import com.example.demo.repo.PaidTreatmentPreparationRepository;
import com.example.demo.repo.PaidTreatmentRepository;
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
@RequestMapping("paidTreatmentPreparation")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class PaidTreatmentPreparationController {

    @Autowired
    private PaidTreatmentRepository paidTreatmentRepository;

    @Autowired
    private PaidTreatmentPreparationRepository paidTreatmentPreparationRepository;

    @Autowired
    private MedicalPreparationsRepository medicalPreparationsRepository;

    @GetMapping("/add/{paidTreatment}")
    public String paidTreatmentAdd(PaidTreatmentPreparation paidTreatmentPreparation, PaidTreatment paidTreatment, Model model) {
        var g = new PaidTreatmentPreparationKey();
        g.setPaidTreatmentId(paidTreatment.id);
        paidTreatmentPreparation.setId(g);

        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        return "paidTreatmentPreparation/add";
    }

    @PostMapping("/add")
    public String paidTreatmentPostAdd(
            @ModelAttribute("paidTreatmentPreparation") @Valid PaidTreatmentPreparation paidTreatmentPreparation,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "paidTreatmentPreparation/add";
        }
        paidTreatmentPreparationRepository.save(paidTreatmentPreparation);
        return "redirect:/paidTreatment";
    }

    @GetMapping("/del")
    public String paidTreatmentDel(
            PaidTreatmentPreparation paidTreatmentPreparation) {
        paidTreatmentPreparationRepository.delete(paidTreatmentPreparation);
        return "redirect:../";
    }
}

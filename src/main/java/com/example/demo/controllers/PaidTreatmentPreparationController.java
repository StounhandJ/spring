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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;

@Controller
@RequestMapping("paidTreatmentPreparation")
@PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
public class PaidTreatmentPreparationController {

    @Autowired
    private PaidTreatmentRepository paidTreatmentRepository;

    @Autowired
    private PaidTreatmentPreparationRepository paidTreatmentPreparationRepository;

    @Autowired
    private MedicalPreparationsRepository medicalPreparationsRepository;

    @GetMapping("/add/{paidTreatment}")
    public String paidTreatmentAdd(PaidTreatmentPreparation paidTreatmentPreparation, PaidTreatment paidTreatment, Model model, @RequestParam("redirect") String redirect) {
        var g = new PaidTreatmentPreparationKey();
        g.setPaidTreatmentId(paidTreatment.id);
        paidTreatmentPreparation.setId(g);

        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        model.addAttribute("redirect", redirect);
        return "paidTreatmentPreparation/add";
    }

    @PostMapping("/add")
    public String paidTreatmentPostAdd(
            @ModelAttribute("paidTreatmentPreparation") @Valid PaidTreatmentPreparation paidTreatmentPreparation,
            BindingResult bindingResult,
            @RequestParam("redirect") String redirect
    ) {
        if (bindingResult.hasErrors()) {
            return "paidTreatmentPreparation/add";
        }
        paidTreatmentPreparationRepository.save(paidTreatmentPreparation);
        return "redirect:"+redirect;
    }

    @GetMapping("/del")
    public String paidTreatmentDel(
            @RequestParam("medicalPreparation") String medicalPreparation,
            @RequestParam("count") String count,
            @RequestParam("redirect") String redirect
    ) {
        var mp = medicalPreparationsRepository.findById(Long.parseLong(medicalPreparation, 10)).get();
        var paidTreatmentPreparations = paidTreatmentPreparationRepository.findByMedicalPreparationAndCount(mp, Integer.parseInt(count));

        paidTreatmentPreparationRepository.deleteAll(paidTreatmentPreparations);
        return "redirect:"+redirect;
    }
}

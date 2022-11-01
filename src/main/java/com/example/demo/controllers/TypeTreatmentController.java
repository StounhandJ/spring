package com.example.demo.controllers;

import com.example.demo.models.TypeTreatment;
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
@RequestMapping("typeTreatment")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class TypeTreatmentController {
    @Autowired
    private TypeTreatmentRepository typeTreatmentRepository;

    @GetMapping()
    public String typeTreatmentMain(Model model) {
        Iterable<TypeTreatment> typeTreatments = typeTreatmentRepository.findAll();
        model.addAttribute("typeTreatments", typeTreatments);
        return "typeTreatment/main";
    }

    @GetMapping("/add")
    public String typeTreatmentAdd(TypeTreatment typeTreatment) {
        return "typeTreatment/add";
    }

    @PostMapping("/add")
    public String typeTreatmentPostAdd(
            @ModelAttribute("typeTreatment") @Valid TypeTreatment typeTreatment,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "typeTreatment/add";
        }
        typeTreatmentRepository.save(typeTreatment);
        return "redirect:";
    }

    @GetMapping("/edit/{typeTreatment}")
    public String typeTreatmentEdit(TypeTreatment typeTreatment) {
        return "typeTreatment/edit";
    }

    @PostMapping("/edit/{typeTreatment}")
    public String typeTreatmentPostEdit(
            @ModelAttribute("typeTreatment") @Valid TypeTreatment typeTreatment,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "typeTreatment/edit";
        }
        typeTreatmentRepository.save(typeTreatment);
        return "redirect:../";
    }

    @GetMapping("/show/{typeTreatment}")
    public String typeTreatmentShow(
            TypeTreatment typeTreatment) {
        return "typeTreatment/show";
    }

    @GetMapping("/del/{typeTreatment}")
    public String typeTreatmentDel(
            TypeTreatment typeTreatment) {
        typeTreatmentRepository.delete(typeTreatment);
        return "redirect:../";
    }
}

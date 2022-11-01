package com.example.demo.controllers;

import com.example.demo.models.Cancellation;
import com.example.demo.repo.CancellationRepository;
import com.example.demo.repo.MedicalPreparationsRepository;
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
@RequestMapping("cancellation")
@PreAuthorize("hasAnyAuthority('STOREKEEPER')")
public class CancellationController {
    @Autowired
    private CancellationRepository cancellationRepository;

    @Autowired
    private MedicalPreparationsRepository medicalPreparationsRepository;

    @GetMapping()
    public String cancellationMain(Model model) {
        model.addAttribute("cancellations", cancellationRepository.findAll());
        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        return "cancellation/main";
    }

    @GetMapping("/add")
    public String cancellationAdd(Cancellation cancellation, Model model) {
        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        return "cancellation/add";
    }

    @PostMapping("/add")
    public String cancellationPostAdd(
            @ModelAttribute("cancellation") @Valid Cancellation cancellation,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
            return "cancellation/add";
        }
        cancellationRepository.save(cancellation);
        return "redirect:";
    }

    @GetMapping("/edit/{cancellation}")
    public String cancellationEdit(Cancellation cancellation, Model model) {
        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        return "cancellation/edit";
    }

    @PostMapping("/edit/{cancellation}")
    public String cancellationPostEdit(
            @ModelAttribute("cancellation") @Valid Cancellation cancellation,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
            return "cancellation/edit";
        }
        cancellationRepository.save(cancellation);
        return "redirect:../";
    }

    @GetMapping("/show/{cancellation}")
    public String cancellationShow(
            Cancellation cancellation) {
        return "cancellation/show";
    }

    @GetMapping("/del/{cancellation}")
    public String cancellationDel(
            Cancellation cancellation) {
        cancellationRepository.delete(cancellation);
        return "redirect:../";
    }
}

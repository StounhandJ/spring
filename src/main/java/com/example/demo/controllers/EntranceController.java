package com.example.demo.controllers;

import com.example.demo.models.Entrance;
import com.example.demo.repo.EntranceRepository;
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
@RequestMapping("entrance")
@PreAuthorize("hasAnyAuthority('STOREKEEPER')")
public class EntranceController {
    @Autowired
    private EntranceRepository entranceRepository;

    @Autowired
    private MedicalPreparationsRepository medicalPreparationsRepository;

    @GetMapping()
    public String entranceMain(Model model) {
        model.addAttribute("entrances", entranceRepository.findAll());
        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        return "entrance/main";
    }

    @GetMapping("/add")
    public String entranceAdd(Entrance entrance, Model model) {
        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        return "entrance/add";
    }

    @PostMapping("/add")
    public String entrancePostAdd(
            @ModelAttribute("entrance") @Valid Entrance entrance,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
            return "entrance/add";
        }
        entranceRepository.save(entrance);
        return "redirect:";
    }

    @GetMapping("/edit/{entrance}")
    public String entranceEdit(Entrance entrance, Model model) {
        model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
        return "entrance/edit";
    }

    @PostMapping("/edit/{entrance}")
    public String entrancePostEdit(
            @ModelAttribute("entrance") @Valid Entrance entrance,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("medicalPreparations", medicalPreparationsRepository.findAll());
            return "entrance/edit";
        }
        entranceRepository.save(entrance);
        return "redirect:../";
    }

    @GetMapping("/show/{entrance}")
    public String entranceShow(
            Entrance entrance) {
        return "entrance/show";
    }

    @GetMapping("/del/{entrance}")
    public String entranceDel(
            Entrance entrance) {
        entranceRepository.delete(entrance);
        return "redirect:../";
    }
}

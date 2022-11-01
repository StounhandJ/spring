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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
    public String medicalPreparationsMain(Model model) {
        model.addAttribute("medicalPreparationss", medicalPreparationsRepository.findAll());
        model.addAttribute("shelving", shelvingRepository.findAll());
        model.addAttribute("typePreparations", typePreparationRepository.findAll());
        return "medicalPreparations/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
    public String medicalPreparationsAdd(MedicalPreparations medicalPreparations, Model model) {
        model.addAttribute("shelving", shelvingRepository.findAll());
        model.addAttribute("typePreparations", typePreparationRepository.findAll());
        return "medicalPreparations/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
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
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
    public String medicalPreparationsEdit(MedicalPreparations medicalPreparations, Model model) {
        model.addAttribute("shelving", shelvingRepository.findAll());
        model.addAttribute("typePreparations", typePreparationRepository.findAll());
        return "medicalPreparations/edit";
    }

    @PostMapping("/edit/{medicalPreparations}")
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
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
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
    public String medicalPreparationsDel(
            MedicalPreparations medicalPreparations) {
        medicalPreparationsRepository.delete(medicalPreparations);
        return "redirect:../";
    }

    @GetMapping("/filter")
    public String bookFilter(@RequestParam(defaultValue = "") String title,
                             @RequestParam(required = false) boolean accurate_search,
                             Model model) {
        if (!title.equals("")) {
            List<MedicalPreparations> result = accurate_search ? medicalPreparationsRepository.findByName(title) : medicalPreparationsRepository.findByNameContains(title);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", title);
        model.addAttribute("accurate_search", accurate_search);
        return "medicalPreparations/filter";
    }
}

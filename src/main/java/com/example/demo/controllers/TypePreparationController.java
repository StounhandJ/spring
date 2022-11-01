package com.example.demo.controllers;

import com.example.demo.models.TypePreparation;
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
@RequestMapping("typePreparation")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class TypePreparationController {
    @Autowired
    private TypePreparationRepository typePreparationRepository;

    @GetMapping()
    public String typePreparationMain(Model model) {
        Iterable<TypePreparation> typePreparations = typePreparationRepository.findAll();
        model.addAttribute("typePreparations", typePreparations);
        return "typePreparation/main";
    }

    @GetMapping("/add")
    public String typePreparationAdd(TypePreparation typePreparation) {
        return "typePreparation/add";
    }

    @PostMapping("/add")
    public String typePreparationPostAdd(
            @ModelAttribute("typePreparation") @Valid TypePreparation typePreparation,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "typePreparation/add";
        }
        typePreparationRepository.save(typePreparation);
        return "redirect:";
    }

    @GetMapping("/edit/{typePreparation}")
    public String typePreparationEdit(TypePreparation typePreparation) {
        return "typePreparation/edit";
    }

    @PostMapping("/edit/{typePreparation}")
    public String typePreparationPostEdit(
            @ModelAttribute("typePreparation") @Valid TypePreparation typePreparation,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "typePreparation/edit";
        }
        typePreparationRepository.save(typePreparation);
        return "redirect:../";
    }

    @GetMapping("/show/{typePreparation}")
    public String typePreparationShow(
            TypePreparation typePreparation) {
        return "typePreparation/show";
    }

    @GetMapping("/del/{typePreparation}")
    public String typePreparationDel(
            TypePreparation typePreparation) {
        typePreparationRepository.delete(typePreparation);
        return "redirect:../";
    }
}

package com.example.demo.controllers;

import com.example.demo.models.Shelving;
import com.example.demo.repo.ShelvingRepository;
import com.example.demo.repo.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("shelving")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ShelvingController {
    @Autowired
    private ShelvingRepository shelvingRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping()
    public String shelvingMain(Model model) {
        model.addAttribute("shelvings", shelvingRepository.findAll());
        model.addAttribute("warehouses", warehouseRepository.findAll());
        return "shelving/main";
    }

    @GetMapping("/add")
    public String shelvingAdd(Shelving shelving, Model model) {
        model.addAttribute("warehouses", warehouseRepository.findAll());
        return "shelving/add";
    }

    @PostMapping("/add")
    public String shelvingPostAdd(
            @ModelAttribute("shelving") @Valid Shelving shelving,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("warehouses", warehouseRepository.findAll());
            return "shelving/add";
        }
        shelvingRepository.save(shelving);
        return "redirect:";
    }

    @GetMapping("/edit/{shelving}")
    public String shelvingEdit(Shelving shelving, Model model) {
        model.addAttribute("warehouses", warehouseRepository.findAll());
        return "shelving/edit";
    }

    @PostMapping("/edit/{shelving}")
    public String shelvingPostEdit(
            @ModelAttribute("shelving") @Valid Shelving shelving,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("warehouses", warehouseRepository.findAll());
            return "shelving/edit";
        }
        shelvingRepository.save(shelving);
        return "redirect:../";
    }

    @GetMapping("/show/{shelving}")
    public String shelvingShow(
            Shelving shelving) {
        return "shelving/show";
    }

    @GetMapping("/del/{shelving}")
    public String shelvingDel(
            Shelving shelving) {
        shelvingRepository.delete(shelving);
        return "redirect:../";
    }

    @GetMapping("/filter")
    public String bookFilter(@RequestParam(defaultValue = "") String title,
                             @RequestParam(required = false) boolean accurate_search,
                             Model model) {
        if (!title.equals("")) {
            List<Shelving> result = accurate_search ? shelvingRepository.findByName(title) : shelvingRepository.findByNameContains(title);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", title);
        model.addAttribute("accurate_search", accurate_search);
        return "shelving/filter";
    }
}

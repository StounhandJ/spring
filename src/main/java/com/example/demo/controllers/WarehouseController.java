package com.example.demo.controllers;

import com.example.demo.models.Warehouse;
import com.example.demo.repo.WarehouseRepository;
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
@RequestMapping("warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping()
    public String warehouseMain(Model model) {
        Iterable<Warehouse> warehouses = warehouseRepository.findAll();
        model.addAttribute("warehouses", warehouses);
        return "warehouse/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String warehouseAdd(Warehouse warehouse) {
        return "warehouse/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String warehousePostAdd(
            @ModelAttribute("warehouse") @Valid Warehouse warehouse,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "warehouse/add";
        }
        warehouseRepository.save(warehouse);
        return "redirect:";
    }

    @GetMapping("/edit/{warehouse}")
    @PreAuthorize("isAuthenticated()")
    public String warehouseEdit(Warehouse warehouse) {
        return "warehouse/edit";
    }

    @PostMapping("/edit/{warehouse}")
    @PreAuthorize("isAuthenticated()")
    public String warehousePostEdit(
            @ModelAttribute("warehouse") @Valid Warehouse warehouse,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "warehouse/edit";
        }
        warehouseRepository.save(warehouse);
        return "redirect:../";
    }

    @GetMapping("/show/{warehouse}")
    public String warehouseShow(
            Warehouse warehouse) {
        return "warehouse/show";
    }

    @GetMapping("/del/{warehouse}")
    @PreAuthorize("isAuthenticated()")
    public String warehouseDel(
            Warehouse warehouse) {
        warehouseRepository.delete(warehouse);
        return "redirect:../";
    }
}

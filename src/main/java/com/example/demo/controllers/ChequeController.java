package com.example.demo.controllers;

import com.example.demo.models.Cheque;
import com.example.demo.models.PaidTreatment;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.ChequeRepository;
import com.example.demo.repo.PaidTreatmentRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("cheque")
@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'DOCTOR')")
public class ChequeController {
    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private PaidTreatmentRepository paidTreatmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String chequeMain(Model model) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRoles().contains(Role.CLIENT)) {
            model.addAttribute("cheques", chequeRepository.findByPaidTreatmentApplicationClient_Id(user.getId()));
        } else {
            model.addAttribute("cheques", chequeRepository.findAll());
        }

        model.addAttribute("cheques", chequeRepository.findAll());
        model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        return "cheque/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequeAdd(Cheque cheque, Model model) {
        model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        return "cheque/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequePostAdd(
            @ModelAttribute("cheque") @Valid Cheque cheque,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
            return "cheque/add";
        }
        chequeRepository.save(cheque);
        return "redirect:";
    }

    @GetMapping("/add/{paidTreatment}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequeAddPaidTreatment(Cheque cheque, PaidTreatment paidTreatment, Model model, @RequestParam("redirect") String redirect) {
        cheque.setPaidTreatment(paidTreatment);
        model.addAttribute("redirect", redirect);
        return "cheque/addPaidTreatment";
    }

    @PostMapping("/add/{paidTreatment}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequePostAddPaidTreatment(
            @ModelAttribute("cheque") @Valid Cheque cheque,
            PaidTreatment paidTreatment,
            BindingResult bindingResult,
            Model model,
            @RequestParam("redirect") String redirect
    ) {
        if (bindingResult.hasErrors()) {
            cheque.setPaidTreatment(paidTreatment);
            model.addAttribute("redirect", redirect);
            return "cheque/addPaidTreatment";
        }
        chequeRepository.save(cheque);
        return "redirect:"+redirect;
    }

    @GetMapping("/edit/{cheque}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequeEdit(Cheque cheque, Model model) {
        model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
        return "cheque/edit";
    }

    @PostMapping("/edit/{cheque}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequePostEdit(
            @ModelAttribute("cheque") @Valid Cheque cheque,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("paidTreatments", paidTreatmentRepository.findAll());
            return "cheque/edit";
        }
        chequeRepository.save(cheque);
        return "redirect:../";
    }

    @GetMapping("/show/{cheque}")
    public String chequeShow(
            Cheque cheque, Model model) {
        model.addAttribute("cheque", cheque);
        return "cheque/show";
    }

    @GetMapping("/del/{cheque}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String chequeDel(
            Cheque cheque) {
        chequeRepository.delete(cheque);
        return "redirect:../";
    }
}

package com.example.demo.controllers;

import com.example.demo.models.JobApplication;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.JobApplicationRepository;
import com.example.demo.repo.ShelvingRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("jobApplication")
@PreAuthorize("hasAnyAuthority('HR', 'CLIENT')")
public class JobApplicationController {
    @Autowired
    private JobApplicationRepository jobApplicationsRepository;

    @Autowired
    private ShelvingRepository shelvingRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping()
    public String jobApplicationsMain(Model model, @RequestParam(required = false, name = "f") boolean no_active) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRoles().contains(Role.CLIENT)) {
            if (no_active) {
                model.addAttribute("jobApplications", jobApplicationsRepository.findByCandidate_Id(user.getId()));
            } else {
                model.addAttribute("jobApplications", jobApplicationsRepository.findByCandidate_IdActive(user.getId()));
            }
        } else {
            if (no_active) {
                model.addAttribute("jobApplications", jobApplicationsRepository.findAll());
            } else {
                model.addAttribute("jobApplications", jobApplicationsRepository.findActive());
            }
        }

        model.addAttribute("no_active", no_active);

        return "jobApplication/main";
    }

    @GetMapping("/add")
    public String jobApplicationsAdd(JobApplication jobApplication, Model model) {
        jobApplication.setCandidate(userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
        jobApplication.setDate(new Date(System.currentTimeMillis()));
        return "jobApplication/add";
    }

    @PostMapping("/add")
    public String jobApplicationsPostAdd(
            @ModelAttribute("jobApplication") @Valid JobApplication jobApplication,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            jobApplication.setCandidate(userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
            jobApplication.setDate(new Date(System.currentTimeMillis()));
            return "jobApplication/add";
        }
        jobApplicationsRepository.save(jobApplication);
        return "redirect:";
    }

    @GetMapping("/edit/{jobApplication}")
    public String jobApplicationsEdit(JobApplication jobApplication, Model model) {
        model.addAttribute("shelving", shelvingRepository.findAll());
        return "jobApplication/edit";
    }

    @PostMapping("/edit/{jobApplication}")
    public String jobApplicationsPostEdit(
            @ModelAttribute("jobApplication") @Valid JobApplication jobApplication,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shelving", shelvingRepository.findAll());
            return "jobApplication/edit";
        }
        jobApplicationsRepository.save(jobApplication);
        return "redirect:../";
    }

    @GetMapping("/show/{jobApplication}")
    public String jobApplicationsShow(
            JobApplication jobApplication) {
        return "jobApplication/show";
    }

    @GetMapping("/del/{jobApplications}")
    @PreAuthorize("hasAnyAuthority('STOREKEEPER')")
    public String jobApplicationsDel(
            JobApplication jobApplications) {
        jobApplicationsRepository.delete(jobApplications);
        return "redirect:../";
    }
}

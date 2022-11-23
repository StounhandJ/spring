package com.example.demo.controllers;

import com.example.demo.models.Application;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.models.json.Graphs;
import com.example.demo.repo.ApplicationRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("application")
@PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR', 'CLIENT')")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String applicationMain(Model model) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRoles().contains(Role.CLIENT)) {
            model.addAttribute("applications", applicationRepository.findByClient_Id(user.getId()));
        } else {
            model.addAttribute("applications", applicationRepository.findAll());
        }

        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        return "application/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String applicationAdd(Application application, Model model) {
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        return "application/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String applicationPostAdd(
            @ModelAttribute("application") @Valid Application application,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
            model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
            return "application/add";
        }
        applicationRepository.save(application);
        return "redirect:";
    }

    @GetMapping("/edit/{application}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String applicationEdit(Application application, Model model) {
        model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
        model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
        model.addAttribute("appli", application);
        return "application/edit";
    }

    @PostMapping("/edit/{application}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String applicationPostEdit(
            @ModelAttribute("application") @Valid Application application,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", userRepository.findByRoles(Role.CLIENT));
            model.addAttribute("doctors", userRepository.findByRoles(Role.DOCTOR));
            model.addAttribute("appli", application);
            return "application/edit";
        }
        applicationRepository.save(application);
        return "redirect:../";
    }

    @GetMapping("/show/{application}")
    public String applicationShow(
            Application application, Model model) {
        model.addAttribute("appli", application);
        return "application/show";
    }

    @GetMapping("/del/{application}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String applicationDel(
            Application application) {
        applicationRepository.delete(application);
        return "redirect:../";
    }

    @GetMapping("graph")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    public String graphs() {
        return "application/graph";
    }

    @GetMapping("graph/data")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    @ResponseBody
    public Graphs test() throws ParseException {
        Map<String, Integer> applicationData = new HashMap<>();

        for (Application application : applicationRepository.findAll()) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(application.application_date);
            if (applicationData.containsKey(date)) {
                applicationData.compute(date, (k, v) -> v + 1);
            } else {
                applicationData.put(date, 1);
            }
        }

        applicationData = new TreeMap<>(applicationData);
        if (applicationData.size() > 0) {
            Date firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(applicationData.keySet().stream().toArray()[0].toString());
            Date lastDate = new SimpleDateFormat("yyyy-MM-dd").parse(applicationData.keySet().stream().toArray()[applicationData.size() - 1].toString());

            while (firstDate.before(lastDate)) {
                Calendar c = Calendar.getInstance();
                c.setTime(firstDate);
                c.add(Calendar.DATE, 1);
                firstDate = c.getTime();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
                if (!applicationData.containsKey(date)){
                    applicationData.put(date, 0);
                }
            }
        }

        Graphs graphs = new Graphs();
        graphs.data = new ArrayList<>();
        graphs.dates = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : applicationData.entrySet()) {
            graphs.data.add(entry.getValue());
            graphs.dates.add(entry.getKey());
        }

        return graphs;
    }
}

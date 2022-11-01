package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.models.User;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.UserRepository;
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
import java.util.List;

@Controller
@RequestMapping("employee")
@PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String employeeMain(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("users", userRepository.findActive());
        return "employee/main";
    }

    @GetMapping("/add")
    public String employeeAdd(Employee employee, Model model) {
        List<User> users =  userRepository.findActive();
        model.addAttribute("users", userRepository.findActive());
        return "employee/add";
    }

    @PostMapping("/add")
    public String employeePostAdd(
            @ModelAttribute("employee") @Valid Employee employee,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userRepository.findActive());
            return "employee/add";
        }
        employeeRepository.save(employee);
        return "redirect:";
    }

    @GetMapping("/edit/{employee}")
    public String employeeEdit(Employee employee, Model model) {
        model.addAttribute("users", userRepository.findActive());
        return "employee/edit";
    }

    @PostMapping("/edit/{employee}")
    public String employeePostEdit(
            @ModelAttribute("employee") @Valid Employee employee,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userRepository.findActive());
            return "employee/edit";
        }
        employeeRepository.save(employee);
        return "redirect:../";
    }

    @GetMapping("/show/{employee}")
    public String employeeShow(
            Employee employee, Model model) {
        model.addAttribute("employee", employee);
        return "employee/show";
    }

    @GetMapping("/del/{employee}")
    public String employeeDel(
            Employee employee) {
        employeeRepository.delete(employee);
        return "redirect:../";
    }
}

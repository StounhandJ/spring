package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.models.User;
import com.example.demo.repo.ClientRepository;
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
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String clientMain(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("users", userRepository.findActive());
        return "client/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String clientAdd(Client client, Model model) {
        List<User> users =  userRepository.findActive();
        model.addAttribute("users", userRepository.findActive());
        return "client/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String clientPostAdd(
            @ModelAttribute("client") @Valid Client client,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userRepository.findActive());
            return "client/add";
        }
        clientRepository.save(client);
        return "redirect:";
    }

    @GetMapping("/edit/{client}")
    @PreAuthorize("isAuthenticated()")
    public String clientEdit(Client client, Model model) {
        model.addAttribute("users", userRepository.findActive());
        return "client/edit";
    }

    @PostMapping("/edit/{client}")
    @PreAuthorize("isAuthenticated()")
    public String clientPostEdit(
            @ModelAttribute("client") @Valid Client client,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userRepository.findActive());
            return "client/edit";
        }
        clientRepository.save(client);
        return "redirect:../";
    }

    @GetMapping("/show/{client}")
    public String clientShow(
            Client client) {
        return "client/show";
    }

    @GetMapping("/del/{client}")
    @PreAuthorize("isAuthenticated()")
    public String clientDel(
            Client client) {
        clientRepository.delete(client);
        return "redirect:../";
    }
}

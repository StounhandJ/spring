package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("author")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping()
    public String authorMain(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author/main";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String authorAdd(Author author) {
        return "author/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String authorPostAdd(
            @ModelAttribute("author") @Valid Author author,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()){
            return "author/add";
        }
        authorRepository.save(author);
        return "redirect:";
    }

    @GetMapping("/edit/{author}")
    @PreAuthorize("isAuthenticated()")
    public String authorEdit(
            Author author,
            Model model) {
        model.addAttribute("birthday", new SimpleDateFormat("yyyy-MM-dd").format(author.birthday));
        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/edit/{author}")
    @PreAuthorize("isAuthenticated()")
    public String authorPostEdit(
            @ModelAttribute("author") @Valid Author author,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()){
            return "author/edit";
        }
        authorRepository.save(author);
        return "redirect:../";
    }

    @GetMapping("/show/{author}")
    public String authorShow(
            Author author,
            Model model) {
        model.addAttribute("birthday", new SimpleDateFormat("yyyy-MM-dd").format(author.birthday));
        model.addAttribute("author", author);
        return "author/show";
    }

    @GetMapping("/del/{author}")
    @PreAuthorize("isAuthenticated()")
    public String authorDel(
            Author author) {
        authorRepository.delete(author);
        return "redirect:../";
    }

    @GetMapping("/filter")
    public String authorFilter(@RequestParam(defaultValue = "") String name,
                               @RequestParam(required = false) boolean accurate_search,
                               Model model) {
        if (!name.equals("")) {
            List<Author> result = accurate_search ? authorRepository.findByName(name) : authorRepository.findByNameContains(name);
            model.addAttribute("result", result);
        }

        model.addAttribute("name", name);
        model.addAttribute("accurate_search", accurate_search);
        return "author/filter";
    }


}

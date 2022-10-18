package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

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
    public String authorAdd() {
        return "author/add";
    }

    @PostMapping("/add")
    public String authorPostAdd(
            @RequestParam String name,
            @RequestParam Date birthday,
            @RequestParam boolean gender,
            @RequestParam int start_year,
            @RequestParam double budget
    ) {
        Author book = new Author(name, birthday, gender, start_year, budget);
        authorRepository.save(book);
        return "redirect:";
    }

    @GetMapping("/edit/{author}")
    public String authorEdit(
            Author author,
            Model model) {
        model.addAttribute("birthday", new SimpleDateFormat("yyyy-MM-dd").format(author.birthday));
        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/edit/{author}")
    public String authorPostEdit(
            @RequestParam String name,
            @RequestParam Date birthday,
            @RequestParam boolean gender,
            @RequestParam int start_year,
            @RequestParam double budget,
            Author author
    ) {
        author.name = name;
        author.birthday = birthday;
        author.gender = gender;
        author.start_year = start_year;
        author.budget = budget;
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

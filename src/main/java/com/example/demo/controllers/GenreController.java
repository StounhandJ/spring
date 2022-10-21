package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Genre;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("genre")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping()
    public String bookMain(Model model) {
        Iterable<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "genre/main";
    }

    @GetMapping("/add")
    public String bookAdd(Genre genre, Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "genre/add";
    }

    @PostMapping("/add")
    public String bookPostAdd(
            @ModelAttribute("genre") @Valid Genre genre,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Iterable<Author> authors = authorRepository.findAll();
            model.addAttribute("authors", authors);
            return "genre/add";
        }
        genreRepository.save(genre);
        return "redirect:";
    }

    @GetMapping("/edit/{genre}")
    public String bookEdit(Genre genre, Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "genre/edit";
    }

    @PostMapping("/edit/{genre}")
    public String bookPostEdit(
            @ModelAttribute("genre") @Valid Genre genre,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Iterable<Author> authors = authorRepository.findAll();
            model.addAttribute("authors", authors);
            return "genre/edit";
        }
        genreRepository.save(genre);
        return "redirect:../";
    }

    @GetMapping("/show/{genre}")
    public String bookShow(
            Genre genre) {
        return "genre/show";
    }

    @GetMapping("/del/{genre}")
    public String bookDel(
            Genre genre) {
        genreRepository.delete(genre);
        return "redirect:../";
    }
}

package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("book")
@PreAuthorize("hasAnyAuthority('USER')")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping()
    public String bookMain(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/main";
    }

    @GetMapping("/add")
    public String bookAdd(Book book, Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book/add";
    }

    @PostMapping("/add")
    public String bookPostAdd(
            @ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Iterable<Author> authors = authorRepository.findAll();
            Iterable<Genre> genres = genreRepository.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "book/add";
        }
        bookRepository.save(book);
        return "redirect:";
    }

    @GetMapping("/edit/{book}")
    public String bookEdit(
            Book book,
            Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book/edit";
    }

    @PostMapping("/edit/{book}")
    public String bookPostEdit(
            @ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Iterable<Author> authors = authorRepository.findAll();
            Iterable<Genre> genres = genreRepository.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "book/edit";
        }
        bookRepository.save(book);
        return "redirect:../";
    }

    @GetMapping("/show/{book}")
    public String bookShow(
            Book book,
            Model model) {
        model.addAttribute("release_date", new SimpleDateFormat("yyyy-MM-dd").format(book.release_date));
        model.addAttribute("book", book);
        return "book/show";
    }

    @GetMapping("/del/{book}")
    public String bookDel(
            Book book) {
        bookRepository.delete(book);
        return "redirect:../";
    }

    @GetMapping("/filter")
    public String bookFilter(@RequestParam(defaultValue = "") String title,
                             @RequestParam(required = false) boolean accurate_search,
                             Model model) {
        if (!title.equals("")) {
            List<Book> result = accurate_search ? bookRepository.findByTitle(title) : bookRepository.findByTitleContains(title);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", title);
        model.addAttribute("accurate_search", accurate_search);
        return "book/filter";
    }


}

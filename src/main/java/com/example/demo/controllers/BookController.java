package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping()
    public String bookMain(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/main";
    }

    @GetMapping("/add")
    public String bookAdd(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "book/add";
    }

    @PostMapping("/add")
    public String bookPostAdd(
            @RequestParam String title,
            @RequestParam Author author,
            @RequestParam Date release_date,
            @RequestParam boolean for_sale,
            @RequestParam double price
    ) {
        Book book = new Book(title, author, release_date, for_sale, price);
        bookRepository.save(book);
        return "redirect:";
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

package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("blog")
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping()
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog/main";
    }

    @GetMapping("/add")
    public String blogAdd(Model model) {
        return "blog/add";
    }

    @PostMapping("/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:";
    }

    @GetMapping("/filter/result")
    public String blogFilter(@RequestParam(defaultValue = "") String title,
                             @RequestParam(required = false) boolean accurate_search,
                             Model model) {
        if (!title.equals("")) {
            List<Post> result = accurate_search ? postRepository.findByTitle(title) : postRepository.findByTitleContains(title);
            model.addAttribute("result", result);
        }

        model.addAttribute("title", title);
        model.addAttribute("accurate_search", accurate_search);
        return "blog/filter";
    }


}

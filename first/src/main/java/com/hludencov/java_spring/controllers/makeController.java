package com.hludencov.java_spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class makeController {
    @GetMapping("/")
    public String home() {
        return "calculate";
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam(name = "firstval", required = false, defaultValue = "0") double firstval,
                            @RequestParam(name = "secval", required = false, defaultValue = "0") double secval,
                            @RequestParam(name = "math_operation", required = false, defaultValue = "+") String mathval, Model model) {
        return solving(firstval, secval, mathval, model);
    }
    @PostMapping("/calculate")
    public String POSTCalculate(@RequestParam(name = "firstval", required = false, defaultValue = "0") double firstval,
                            @RequestParam(name = "secval", required = false, defaultValue = "0") double secval,
                            @RequestParam(name = "math_operation", required = false, defaultValue = "+") String mathval, Model model) {
        return solving(firstval, secval, mathval, model);
    }

    private String solving(double firstval,
                           double secval,
                           String mathval,
                           Model model) {
        double res = switch (mathval) {
            case "+"-> firstval + secval;
            case "-"->firstval - secval;
            case "*"-> firstval * secval;
            case "/"-> firstval / secval;
            default-> 0;
        };
        model.addAttribute("res", res);

        return "result";
    }


}

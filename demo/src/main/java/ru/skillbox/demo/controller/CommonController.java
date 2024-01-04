package ru.skillbox.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CommonController {

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }
}
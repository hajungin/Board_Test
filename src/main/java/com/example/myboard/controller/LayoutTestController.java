package com.example.myboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutTestController {
    @GetMapping("/test")
    public String testView(Model model){
        model.addAttribute("name", "하정인");
        return "test";
    }
}

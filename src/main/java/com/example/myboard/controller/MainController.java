package com.example.myboard.controller;

import com.example.myboard.dto.ArticleDto;
import com.example.myboard.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String mainView(Model model){
        List<ArticleDto> articleDtoList = articleService.show();
        model.addAttribute("articleDto", articleDtoList);
        return "articles/show_all";
    }


}

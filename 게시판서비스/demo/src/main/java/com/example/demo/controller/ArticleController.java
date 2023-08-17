package com.example.demo.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticleController {


    @GetMapping
    public String articles(ModelMap map){
        map.addAttribute("articles", List.of());
        return "articles/index";
    }
    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap map){
        map.addAttribute("article", null);
        map.addAttribute("articleComment", List.of());
        return "articles/detail";
    }

}

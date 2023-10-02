package com.example.demo.controller;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.ArticleWithCommentsResponse;
import com.example.demo.service.ArticleService;
import com.example.demo.service.PaginationService;
import com.example.demo.type.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;


    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
            ,ModelMap map){
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(articles.getNumber(), articles.getTotalPages());
        map.addAttribute("articles",articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        return "articles/index";
    }
    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap map){
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComment", article.articleCommentsResponse());
        return "articles/detail";
    }

}

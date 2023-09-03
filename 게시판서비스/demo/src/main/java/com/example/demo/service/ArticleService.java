package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleUpdateDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.type.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {

    }

    public void updateArticle(long articleId , ArticleUpdateDto dto) {
    }

    public void deleteArticle(long articleId) {
    }
}

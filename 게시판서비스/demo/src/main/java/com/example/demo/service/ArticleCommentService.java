package com.example.demo.service;

import com.example.demo.domain.ArticleComment;
import com.example.demo.repository.ArticleCommentRepository;
import com.example.demo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepo;
    private final ArticleCommentRepository articleCommentRepo;
@Transactional(readOnly = true)
    public List<ArticleComment> searchArticleComments(Long articleId) {
        return null;
    }
}

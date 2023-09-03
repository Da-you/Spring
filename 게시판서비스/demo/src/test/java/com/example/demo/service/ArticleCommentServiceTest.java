package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleComment;
import com.example.demo.dto.ArticleCommentDto;
import com.example.demo.repository.ArticleCommentRepository;
import com.example.demo.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글 ")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {
    @InjectMocks
    private ArticleCommentService sut;
    @Mock
    private ArticleRepository articleRepo;
    @Mock
    private ArticleCommentRepository articleCommentRepo;

    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // given
        Long articleId = 1l;
        given(articleRepo.findById(articleId)).willReturn(Optional.of(
                Article.of("title", "content", "#java")));
        // when
        List<ArticleComment> articleComments =
                sut.searchArticleComments(articleId);
        // then
        assertThat(articleComments).isNotNull();
        then(articleRepo).should().findById(articleId);
    }
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // given
        Long articleId = 1l;
        given(articleRepo.findById(articleId)).willReturn(Optional.of(
                Article.of("title", "content", "#java")));
        // when
        List<ArticleComment> articleComments =
                sut.searchArticleComments(articleId);
        // then
        assertThat(articleComments).isNotNull();
        then(articleRepo).should().findById(articleId);
    }

}
package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.dto.ArticleWithCommentsDto;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class )
class ArticleControllerTest {

    private final MockMvc mvc;
    @MockBean
    private ArticleService articleService;
    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }


     @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticles_thenReturnArticlesView() throws Exception {
         given(articleService.searchArticles(eq(null),eq(null), any(Pageable.class)))
                 .willReturn(Page.empty());
      mvc.perform(get("/articles"))
              .andExpect(status().isOk())
              .andExpect(view().name("articles/index"))
              .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
              .andExpect(model().attributeExists("articles"));
      then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));
    }

    @Test
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
        mvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
        then(articleService).should().getArticle(articleId);
    }
    @Test
    @DisplayName("[view][GET] 게시글 검색 전용  페이지 - 정상 호출")
    void givenNothing_whenRequestingArticleSearchView_thenReturnArticleView() throws Exception {
        mvc.perform(get("/articles/search "))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));
     }
    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnArticleView() throws Exception {
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search-hashtag"));

    }
    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
               "hashtag",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "pw",
                "uno@mail.com",
                "Uno",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

}
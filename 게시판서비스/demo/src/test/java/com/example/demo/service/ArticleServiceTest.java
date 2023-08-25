package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.type.SearchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;
import static org.assertj.core.api.Assertions.* ;
@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        // given
        // when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "Search keyword"); // 제목, 본문,ID,닉네임, 해시태그
        // then
        assertThat(articles).isNotNull();
    }

    @Test
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // given
        // when
        ArticleDto article  = sut.searchArticle(1l); // 제목, 본문,ID,닉네임, 해시태그
        // then
        assertThat(article).isNotNull();
    }

}

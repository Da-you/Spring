package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleUpdateDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.type.SearchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.* ;
import static org.mockito.BDDMockito.*;

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
    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // given
        // when
        ArticleDto article  = sut.searchArticle(1l); // 제목, 본문,ID,닉네임, 해시태그
        // then
        assertThat(article).isNotNull();
    }

    @Test
    @DisplayName("게시글을 정보를 입력하면, 게시글을 저장한다.")
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // given
         //  mockito를 사용하여 articleRepository.save(dto)가 호출되었는지 검증
       given(articleRepository.save(any(Article.class))).willReturn(null); // articleRepository.save(dto)가 호출되었는지 검증
        // when
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(),"Huynwoo","제목","본문","해시태그" )); // 제목, 본문,ID,닉네임, 해시태그
        // then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    void givenArticleIdANDModifiedInfo_whenSavingArticle_thenUpdatesArticle() {
        // given
        //  mockito를 사용하여 articleRepository.save(dto)가 호출되었는지 검증
        given(articleRepository.save(any(Article.class))).willReturn(null); // articleRepository.save(dto)가 호출되었는지 검증
        // when
        sut.updateArticle (1L, ArticleUpdateDto.of("제목","본문","해시태그" )); // 제목, 본문,ID,닉네임, 해시태그
        // then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // given
        //  mockito를 사용하여 articleRepository.save(dto)가 호출되었는지 검증
        willDoNothing().given(articleRepository.save(any(Article.class))); // articleRepository.save(dto)가 호출되었는지 검증
        // when
        sut.deleteArticle (1L); // 제목, 본문,ID,닉네임, 해시태그
        // then
        then(articleRepository).should().delete(any(Article.class));
    }

}

package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.domain.UserAccount;
import com.example.demo.domain.type.SearchType;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleWithCommentsDto;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.* ;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @Test
    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList(SearchType searchType, String searchKeyword) {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }
    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }
    @DisplayName("검색어 없이 게시글을 검색하면, 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsArticleEmptyPage() {
        // Given
        Pageable pageable = Pageable.ofSize(20);

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).shouldHaveNoInteractions(); // 상호작용이 없어야 한다.
    }

    @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenHashtag_whenSearchingArticlesViaHashtag_thenReturnsArticlesPage() {
        // Given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashtag(hashtag, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByHashtag(hashtag,pageable); // 상호작용이 없어야 한다.
    }




    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
        ArticleWithCommentsDto dto = sut.getArticle(articleId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }
    // any 키워드는 모든 객체를 의미한다.

    @Test
    @DisplayName("게시글을 정보를 입력하면, 게시글을 저장한다.")
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // given
        ArticleDto dto = createArticleDto();
         //  mockito를 사용하여 articleRepository.save(dto)가 호출되었는지 검증
       given(articleRepository.save(any(Article.class))).willReturn(null); // articleRepository.save(dto)가 호출되었는지 검증
        // when
        sut.saveArticle(dto); // 제목, 본문,ID,닉네임, 해시태그
        // then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    void givenArticleIdANDModifiedInfo_whenSavingArticle_thenUpdatesArticle() {
        // given
        Article article = createArticle();
        ArticleDto dto = createArticleDto("새 제목","본문");
        //  mockito를 사용하여 articleRepository.save(dto)가 호출되었는지 검증
        given(articleRepository.getReferenceById(dto.id())).willReturn(article); // articleRepository.save(dto)가 호출되었는지 검증
        // when
        sut.updateArticle (dto); // 제목, 본문,ID,닉네임, 해시태그
        // then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.title())
                        .hasFieldOrPropertyWithValue("content", dto.content())
                                .hasFieldOrPropertyWithValue("hashtag", dto.hashTag());
        then(articleRepository).should().getReferenceById(dto.id());
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

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다.")
    @Test
    void givenNothing_whenCalling_thenReturnsHashtags() {
        // Given
        Article article = createArticle();
        List<String> expectedHashtags = List.of("java", "spring", "boot");
        given(articleRepository.findAllDistinctHashtags()).willReturn(expectedHashtags);

        // When
        List<String> actualHashtags = sut.getHashtags();

        // Then
        assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashtags();
    }


    private Article createArticle() {
        return createArticle(1L);
    }
//    private Article createArticle(Long id) {
//        Article article = Article.of(
//                createUserAccount(),
//                "title",
//                "content"
//        );
//        article.addHashtags(Set.of(
//                createHashtag(1L, "java"),
//                createHashtag(2L, "spring")
//        ));
//        ReflectionTestUtils.setField(article, "id", id);
//
//        return article;
//    }

    private UserAccount createUserAccount() {
        return createUserAccount("uno");
    }

    private UserAccount createUserAccount(String userId) {
        return UserAccount.of(
                userId,
                "password",
                "uno@email.com",
                "Uno",
                null
        );
    }
    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content");
    }


    private ArticleDto createArticleDto(String title, String content) {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                null,
                LocalDateTime.now(),
                "Uno",
                LocalDateTime.now(),
                "Uno");
    }
    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }



}

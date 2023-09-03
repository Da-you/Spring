package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleUpdateDto;
import com.example.demo.dto.ArticleWithCommentsDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.type.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pagealbe) {
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return articleRepository.findAll(pagealbe).map(ArticleDto::from);
        }
        return switch (searchType) {
            case TITLE ->
                    articleRepository.findByTitleContaining(searchKeyword, pagealbe).map(ArticleDto::from); // 자바 11,13부터 switch문에서 바로 return 가능
            case CONTENT ->
                    articleRepository.findByContentContaining(searchKeyword, pagealbe).map(ArticleDto::from);
            case ID ->
                    articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pagealbe).map(ArticleDto::from);
            case NICKNAME ->
                    articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pagealbe).map(ArticleDto::from);
            case HASHTAG ->
                    articleRepository.findByHashtag("#" + searchKeyword, pagealbe).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(()-> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다. -articleId :  " + articleId));

    }
@Transactional
    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());

    }
@Transactional
    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) article.setTitle(dto.title());
            if (dto.content() != null) article.setContent(dto.content());
            article.setHashtag(dto.hashTag());
        }catch (EntityNotFoundException e){
            log.warn("해당 게시글을 찾을 수 없습니다. -article: { }  " + dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }
}

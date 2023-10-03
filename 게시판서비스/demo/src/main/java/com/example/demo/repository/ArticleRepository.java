package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.QArticle;
import com.example.demo.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom, // QuerydslRepositorySupport를 상속받은 클래스를 사용하기 위해 추가
        QuerydslPredicateExecutor<Article>, // 해당 entity의 필드를 조건으로 검색하는 기능을 추가
        QuerydslBinderCustomizer<QArticle> {


    Page<Article> findByTitleContaining(String title, Pageable pagealbe);
    Page<Article> findByContentContaining(String content, Pageable pagealbe);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pagealbe);
    Page<Article>  findByUserAccount_NicknameContaining(String nickname, Pageable pagealbe);
    Page<Article> findByHashtag(String hashtag , Pageable pagealbe);

    // QuerydslBinderCustomizer를 구현하면 Querydsl에 대한 커스터마이징이 가능
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 기본적으로 제공하는 기능을 제외하고 싶을 때 사용
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색에 사용할 필드를 지정
        bindings.bind(root.title).first(StringExpression::contains); // 제목에 특정 단어가 포함되어 있는지 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 내용에 특정 단어가 포함되어 있는지 검색
        bindings.bind(root.hashtag).first(StringExpression::contains); // 해시태그에 특정 단어가 포함되어 있는지 검색
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 작성일이 특정 날짜와 같은지 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // 작성자에 특정 단어가 포함되어 있는지 검색


    };

}

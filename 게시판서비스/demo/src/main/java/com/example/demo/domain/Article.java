package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Getter
@Entity
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    @Column(nullable = false, length = 10000)
    private String content;
    @Setter
    private String hashtag;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;

    protected Article() {
        // 외부에 노출하지 않기 위해 protected로 생성자 생성
    }
    // 생성자를 통해 값을 받아옴
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    // Article 객체를 생성하는 팩토리 메서드
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null &&id.equals(article.id); // 영속화 되지 않은 엔티티는 id가 없으므로 null 체크(동등성 검사에서 탈락)
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

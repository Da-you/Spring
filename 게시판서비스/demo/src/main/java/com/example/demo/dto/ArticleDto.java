package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(
        LocalDateTime careatedAt,
        String createdBy,
        String title,
        String content,
        String hashTag
)implements Serializable {
    public static ArticleDto of(LocalDateTime careatedAt, String createdBy, String title, String content, String hashTag) {
       return new ArticleDto(careatedAt, createdBy, title, content, hashTag);
    }
}

package com.ssafy.trip.article.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ArticleResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private Long likes;
    private Long memberId;
    private String memberNickname;
}

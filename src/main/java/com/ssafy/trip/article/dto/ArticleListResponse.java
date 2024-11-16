package com.ssafy.trip.article.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleListResponse {
    private List<ArticleResponse> articles;
}

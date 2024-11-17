package com.ssafy.trip.article.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleListWithLastIdResponse {
    private List<ArticleResponse> articles;
    private Long lastId;
}

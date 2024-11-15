package com.ssafy.trip.article.dto;

import com.ssafy.trip.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleListResponse {
    private List<Article> articles;
    private Integer lastId;
}

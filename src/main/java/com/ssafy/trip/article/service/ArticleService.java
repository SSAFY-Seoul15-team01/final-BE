package com.ssafy.trip.article.service;

import com.ssafy.trip.article.dto.ArticleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    Long createArticle(String content, Long memberId, Integer attractionId, List<MultipartFile> images);
    List<ArticleResponse> getRecommendedArticles(Integer pageNumber);
    List<ArticleResponse> getArticlesOfMemberCharacter(Long memberId, Integer sidoId, Long cursorId);
    List<ArticleResponse> getAtriclesOfAttraction(Integer attractionId, Long cursorId);
    Long addLike(Long articleId, Long memberId);
    Long removeLike(Long articleId, Long memberId);
}

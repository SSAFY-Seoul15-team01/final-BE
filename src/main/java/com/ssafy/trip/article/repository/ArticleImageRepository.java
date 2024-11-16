package com.ssafy.trip.article.repository;

import com.ssafy.trip.article.domain.ArticleImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
    @Query("SELECT img.imageUrl " +
            "FROM ArticleImage img " +
            "WHERE img.article.id = :articleId")
    List<String> findImageUrlsByArticleId(@Param("articleId") Long articleId);
}
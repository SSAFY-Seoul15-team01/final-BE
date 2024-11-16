package com.ssafy.trip.article.repository;

import com.ssafy.trip.article.domain.Article;
import jakarta.persistence.Tuple;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a, COUNT(l) as likes " +
            "FROM Article a " +
            "LEFT JOIN Like l ON l.article = a " +
            "LEFT JOIN a.member m " +
            "WHERE a.deletedAt IS NULL " +
            "GROUP BY a " +
            "ORDER BY likes DESC " +
            "LIMIT :pageSize OFFSET :offset")
    List<Tuple> findRecommendArticles(@Param("pageSize") int pageSize, @Param("offset") int offset);
}
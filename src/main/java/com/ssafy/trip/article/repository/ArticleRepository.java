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

    @Query("SELECT a.id articleId, " +
            "a.content articleContent, " +
            "a.createdAt articleCreatedAt, " +
            "(SELECT COUNT(l) FROM Like l WHERE l.article = a) likes, " +
            "m.id memberId, " +
            "m.nickname memberNickname " +
            "FROM Article a " +
            "JOIN a.member m " +
            "JOIN a.attraction att " +
            "JOIN att.gugun g " +
            "JOIN g.sido s " +
            "WHERE a.id < :cursorId " +
            "AND m.id = :memberId " +
            "AND s.no = :sidoId " +
            "AND a.deletedAt IS NULL " +
            "ORDER BY a.id DESC " +
            "LIMIT :pageSize")
    List<Tuple> findArticlesByMemberAndSido(
            @Param("memberId") Long memberId,
            @Param("sidoId") Integer sidoId,
            @Param("cursorId") Long cursorId,
            @Param("pageSize") int pageSize
    );
}
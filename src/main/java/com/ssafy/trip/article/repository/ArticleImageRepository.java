package com.ssafy.trip.article.repository;

import com.ssafy.trip.article.domain.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
}
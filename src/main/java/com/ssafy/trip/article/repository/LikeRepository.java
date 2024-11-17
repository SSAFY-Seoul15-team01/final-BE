package com.ssafy.trip.article.repository;

import com.ssafy.trip.article.domain.Article;
import com.ssafy.trip.article.domain.Like;
import com.ssafy.trip.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndArticle(Member member, Article article);
    Boolean existsByMemberAndArticle(Member member, Article article);
    Long countByArticle(Article article);
}

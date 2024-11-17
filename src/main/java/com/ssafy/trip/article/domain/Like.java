package com.ssafy.trip.article.domain;

import com.ssafy.trip.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"member_id", "article_id"}
        ))
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Like(Member member, Article article) {
        this.member = member;
        this.article = article;
    }
}
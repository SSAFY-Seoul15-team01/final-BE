package com.ssafy.trip.article.domain;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.common.jpa.BaseTimeEntity;
import com.ssafy.trip.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "articles")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    private LocalDateTime deletedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    @Builder
    public Article(String content, Member member, Attraction attraction) {
        this.content = content;
        this.member = member;
        this.attraction = attraction;
    }
}

package com.ssafy.trip.article.domain;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.common.jpa.BaseTimeEntity;
import com.ssafy.trip.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
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
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "attraction_id", referencedColumnName = "no")
    private Attraction attraction;
}

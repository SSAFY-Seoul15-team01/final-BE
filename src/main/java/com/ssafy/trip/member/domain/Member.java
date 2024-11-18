package com.ssafy.trip.member.domain;

import com.ssafy.trip.common.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, length = 100)
    private String nickname;

    private String profileUrl;

    @NotNull
    private String socialId;

    @NotNull
    @Column(length = 30)
    private String socialType;

    private LocalDateTime deletedAt;

    @Builder
    public Member(String nickname, String profileUrl, String socialId, String socialType) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

}
package com.ssafy.trip.member.domain;

import com.ssafy.trip.common.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String nickname;

    private String profileUrl;

    @NonNull
    private String socialId;

    @NonNull
    private String socialType;

    private LocalDateTime deletedAt;

    @Builder
    public Member(String nickname, String profileUrl, String socialId, String socialType, LocalDateTime deletedAt) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.socialId = socialId;
        this.socialType = socialType;
        this.deletedAt = deletedAt;
    }
}
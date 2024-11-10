package com.ssafy.trip.areacharacter.domain;

import com.ssafy.trip.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_characters")
public class MemberCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ColumnDefault("1")
    private Integer level;

    @NotNull
    @ColumnDefault("0")
    private Integer exp;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "character_id")
    private AreaCharacter areaCharacter;

    @Builder
    public MemberCharacter(Integer level, Integer exp, Member member, AreaCharacter areaCharacter) {
        this.level = level;
        this.exp = exp;
        this.member = member;
        this.areaCharacter = areaCharacter;
    }
}

package com.ssafy.trip.character.domain;

import com.ssafy.trip.attraction.domain.Sido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "characters")
public class Character {
    @Id
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    @OneToOne
    @JoinColumn(name = "sido_id")
    private Sido sidoId;
}

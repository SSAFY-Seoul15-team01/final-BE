package com.ssafy.trip.attraction.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "guguns", uniqueConstraints = {
        @UniqueConstraint(
                name = "guguns_sido_to_sidos_cdoe_fk",
                columnNames = "gugun_code"
        )
})

public class Gugun {
    @Id
    private Integer no;

    @NotNull
    private Integer gugunCode;

    @NotNull
    @Column(length = 20)
    private String gugunName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sido_code")
    private Sido sidoCode;
}

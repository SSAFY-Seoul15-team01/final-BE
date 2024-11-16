package com.ssafy.trip.attraction.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "guguns",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"sido_code", "gugun_code"}
        ))
public class Gugun {
    @Id
    private Integer no;

    @Column(name = "sido_code", insertable = false, updatable = false)
    private Integer sidoCode;

    @NotNull
    private Integer gugunCode;

    @NotNull
    @Column(length = 20)
    private String gugunName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sido_code", referencedColumnName = "sidoCode")
    private Sido sido;
}

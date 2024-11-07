package com.ssafy.trip.attraction.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sidos", uniqueConstraints = {
        @UniqueConstraint(
                name = "sido_code_UNIQUE",
                columnNames = "sido_code"
        )
})
public class Sido {
    @Id
    private Integer no;

    @NotNull
    private Integer sidoCode;

    @NotNull
    @Column(length = 20)
    private String sidoName;
}

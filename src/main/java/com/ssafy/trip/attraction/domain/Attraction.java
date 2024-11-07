package com.ssafy.trip.attraction.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "attractions")
public class Attraction {
    @Id
    private Integer no;

    private Integer contentId;

    @NotNull
    @Column(length = 500)
    private String title;

    @NotNull
    @Column(length = 100)
    private String firstImage1;

    @Column(length = 100)
    private String firstImage2;

    @NotNull
    private Integer mapLevel;

    @NotNull
    @Column(precision = 20, scale = 17)
    private BigDecimal latitude;

    @NotNull
    @Column(precision = 20, scale = 17)
    private BigDecimal longitude;

    @Column(length = 20)
    private String tel;

    @NotNull
    @Column(length = 100)
    private String addr1;

    @Column(length = 100)
    private String addr2;

    @Column(length = 1000)
    private String homepage;

    @Column(length = 10000)
    private String overview;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "content_type_id")
    private ContentType contentTypeId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "area_code", referencedColumnName = "sidoCode")
    private Sido areaCode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "si_gun_gu_code", referencedColumnName = "gugunCode")
    private Gugun siGunGuCode;
}

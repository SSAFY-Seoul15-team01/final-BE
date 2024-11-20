package com.ssafy.trip.attraction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Builder
public class AttractionSearchResponse {
    private Integer attractionId;
    private String title;
    private String firstImage1;
    private String firstImage2;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
}

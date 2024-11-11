package com.ssafy.trip.attraction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Builder
public class AttractionResponse {
    private Integer attraction_id;
    private String title;
    private String first_image1;
    private String first_image2;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
}

package com.ssafy.trip.attraction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Builder
public class AttractionNearByResponse {
    private Integer no;
    private String title;
    private Integer row;
    private BigDecimal distance;
}

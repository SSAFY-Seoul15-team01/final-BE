package com.ssafy.trip.areacharacter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AreaCharacterResponse {
    private Integer id;
    private String imageUrl;
    private Integer level;
    private Integer exp;
    private Integer sidoId;
}

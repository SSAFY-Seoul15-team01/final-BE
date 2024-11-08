package com.ssafy.trip.character.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CharacterResponse {
    private Integer id;
    private String imageUrl;
    private Integer level;
    private Integer exp;
    private Integer sidoId;
}

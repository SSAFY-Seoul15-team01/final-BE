package com.ssafy.trip.areacharacter.dto;

import com.ssafy.trip.areacharacter.domain.AreaCharacter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CreatedCharacterResponse {
    AreaCharacter areaCharacter;
}

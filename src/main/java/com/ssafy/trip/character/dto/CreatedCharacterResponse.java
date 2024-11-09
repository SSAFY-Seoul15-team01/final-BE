package com.ssafy.trip.character.dto;

import com.ssafy.trip.character.domain.Character;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CreatedCharacterResponse {
    Character character;
}

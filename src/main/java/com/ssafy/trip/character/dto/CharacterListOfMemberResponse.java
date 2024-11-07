package com.ssafy.trip.character.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class CharacterListOfMemberResponse {
    private List<CharacterResponse> characters;
}

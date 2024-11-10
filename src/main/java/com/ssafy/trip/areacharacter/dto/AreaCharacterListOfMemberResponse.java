package com.ssafy.trip.areacharacter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class AreaCharacterListOfMemberResponse {
    private List<AreaCharacterResponse> areaCharacters;
}

package com.ssafy.trip.character.service;

import com.ssafy.trip.character.domain.MemberCharacter;

import java.util.List;

public interface CharacterService {
    List<MemberCharacter> findCharactersByMemberId(Long memberId);
}

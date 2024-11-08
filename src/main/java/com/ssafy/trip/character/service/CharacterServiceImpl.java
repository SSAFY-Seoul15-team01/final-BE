package com.ssafy.trip.character.service;

import com.ssafy.trip.character.domain.MemberCharacter;
import com.ssafy.trip.character.repository.MemberCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final MemberCharacterRepository memberCharacterRepository;

    @Override
    public List<MemberCharacter> findCharactersByMemberId(Long memberId) {
        return memberCharacterRepository.findByMemberId(memberId);
    }
}

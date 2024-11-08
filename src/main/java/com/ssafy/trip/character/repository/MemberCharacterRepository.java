package com.ssafy.trip.character.repository;

import com.ssafy.trip.character.domain.MemberCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCharacterRepository extends JpaRepository<MemberCharacter, Integer> {
    List<MemberCharacter> findByMemberId(Long memberId);
}

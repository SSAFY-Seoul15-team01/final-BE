package com.ssafy.trip.areacharacter.repository;

import com.ssafy.trip.areacharacter.domain.MemberCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCharacterRepository extends JpaRepository<MemberCharacter, Integer> {
    List<MemberCharacter> findByMemberId(Long memberId);
}

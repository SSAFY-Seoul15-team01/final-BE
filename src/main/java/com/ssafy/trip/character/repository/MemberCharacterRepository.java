package com.ssafy.trip.character.repository;

import com.ssafy.trip.character.domain.MemberCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCharacterRepository extends JpaRepository<MemberCharacter, Integer> {
}

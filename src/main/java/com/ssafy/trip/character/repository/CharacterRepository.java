package com.ssafy.trip.character.repository;

import com.ssafy.trip.attraction.domain.Sido;
import com.ssafy.trip.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
    Character findBySidoId(Sido sidoId);
}

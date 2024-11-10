package com.ssafy.trip.areacharacter.repository;

import com.ssafy.trip.attraction.domain.Sido;
import com.ssafy.trip.areacharacter.domain.AreaCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaCharacterRepository extends JpaRepository<AreaCharacter, Integer> {
    AreaCharacter findBySidoId(Sido sidoId);
}

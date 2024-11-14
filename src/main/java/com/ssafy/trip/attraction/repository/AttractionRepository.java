package com.ssafy.trip.attraction.repository;

import com.ssafy.trip.attraction.domain.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    Optional<Attraction> findByNo(Integer no);
}

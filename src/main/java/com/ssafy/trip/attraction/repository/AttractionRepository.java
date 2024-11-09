package com.ssafy.trip.attraction.repository;

import com.ssafy.trip.attraction.domain.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    Attraction findByNo(Integer no);
}

package com.ssafy.trip.attraction.service;

import com.ssafy.trip.attraction.domain.Attraction;

import java.math.BigDecimal;
import java.util.List;

public interface AttractionService {
    List<Attraction> findAttractionsByKeyword(String keyword, int cursorId);
    List<Attraction> findAttractionsByDistance(BigDecimal latitude, BigDecimal longitude, int cursorId);
}

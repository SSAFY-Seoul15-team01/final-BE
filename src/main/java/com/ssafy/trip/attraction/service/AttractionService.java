package com.ssafy.trip.attraction.service;

import com.ssafy.trip.attraction.domain.Attraction;

import java.util.List;

public interface AttractionService {
    List<Attraction> findAttractionsByKeyword(String keyword, int cursorId);
}

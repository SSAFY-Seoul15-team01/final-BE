package com.ssafy.trip.attraction.service;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.dto.AttractionNearByResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AttractionService {
    List<Attraction> findAttractionsByKeyword(
            String keyword,
            Integer cursorId,
            Boolean spot,
            Boolean facility,
            Boolean festival,
            Boolean leports,
            Boolean stay,
            Boolean shopping,
            Boolean restaurant
    );

    List<AttractionNearByResponse> findAttractionsByDistance(BigDecimal latitude, BigDecimal longitude, int cursorId);
}

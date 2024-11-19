package com.ssafy.trip.attraction.repository;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.dto.AttractionNearByResponse;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AttractionMapper {
    List<Attraction> selectAttractionsByKeyword(String keyword, int cursorId);
    List<AttractionNearByResponse> selectAttractionsByDistance(BigDecimal lat, BigDecimal lng, int cursorId);
}

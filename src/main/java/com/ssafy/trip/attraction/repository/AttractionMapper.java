package com.ssafy.trip.attraction.repository;

import com.ssafy.trip.attraction.domain.Attraction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionMapper {
    List<Attraction> selectAttractionsByKeyword(String keyword, int cursorId);
}

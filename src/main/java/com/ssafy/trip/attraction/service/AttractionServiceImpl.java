package com.ssafy.trip.attraction.service;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionMapper;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {
    private final AttractionMapper attractionMapper;

    @Override
    public List<Attraction> findAttractionsByKeyword(String keyword, int cursorId) {
        return attractionMapper.selectAttractionsByKeyword(keyword, cursorId);
    }

    @Override
    public List<Attraction> findAttractionsByDistance(BigDecimal latitude, BigDecimal longitude, int cursorId) {
        if (latitude == null || longitude == null) {
            throw new BadRequestException(ErrorCode.INVALID_LOCATION);
        }
        return attractionMapper.selectAttractionsByDistance(latitude, longitude, cursorId);
    }
}

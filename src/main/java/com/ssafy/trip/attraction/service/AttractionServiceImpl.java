package com.ssafy.trip.attraction.service;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.dto.AttractionNearByResponse;
import com.ssafy.trip.attraction.repository.AttractionMapper;
import com.ssafy.trip.attraction.util.ContentType;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {
    private final AttractionMapper attractionMapper;

    @Override
    public List<Attraction> findAttractionsByKeyword(
            String keyword,
            Integer cursorId,
            Boolean spot,
            Boolean facility,
            Boolean festival,
            Boolean leports,
            Boolean stay,
            Boolean shopping,
            Boolean restaurant) {
        List<Integer> contentTypes = new ArrayList<>();

        if (spot) {
            contentTypes.add(ContentType.SPOT.getContentTypeId());
        }
        if (facility) {
            contentTypes.add(ContentType.FACILITY.getContentTypeId());
        }
        if (festival) {
            contentTypes.add(ContentType.FESTIVAL.getContentTypeId());
        }
        if (leports) {
            contentTypes.add(ContentType.LEPORTS.getContentTypeId());
        }
        if (stay) {
            contentTypes.add(ContentType.STAY.getContentTypeId());
        }
        if (shopping) {
            contentTypes.add(ContentType.SHOPPING.getContentTypeId());
        }
        if (restaurant) {
            contentTypes.add(ContentType.RESTAURANT.getContentTypeId());
        }

        return attractionMapper.selectAttractionsByKeyword(keyword, cursorId, contentTypes);
    }

    @Override
    public List<AttractionNearByResponse> findAttractionsByDistance(BigDecimal latitude, BigDecimal longitude, int cursorId) {
        return attractionMapper.selectAttractionsByDistance(latitude, longitude, cursorId);
    }
}

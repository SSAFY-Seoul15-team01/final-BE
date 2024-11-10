package com.ssafy.trip.attraction.service;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

package com.ssafy.trip.attraction.controller;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.dto.*;
import com.ssafy.trip.attraction.service.AttractionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractions")
public class AttractionController {
    public final AttractionService attractionService;

    private static final String DEFAULT_CURSOR_ID = "0";

    @GetMapping("/search")
    public ResponseEntity<AttractionSearchPagingResponse> findAttractionsByKeyword(
            @RequestParam @NotBlank String keyword,
            @RequestParam(defaultValue = DEFAULT_CURSOR_ID) Integer cursorId
    ) {
        List<Attraction> attractions = attractionService.findAttractionsByKeyword(keyword, cursorId);

        List<AttractionSearchResponse> attractionDtoList = attractions.stream()
                .map(attraction -> AttractionSearchResponse.builder()
                        .attraction_id(attraction.getNo())
                        .title(attraction.getTitle())
                        .first_image1(attraction.getFirstImage1())
                        .first_image2(attraction.getFirstImage2())
                        .latitude(attraction.getLatitude())
                        .longitude((attraction.getLongitude()))
                        .address(attraction.getAddr1())
                        .build()
                )
                .collect(Collectors.toList());

        AttractionSearchPagingResponse responseDto = AttractionSearchPagingResponse.builder()
                .attractions(attractionDtoList)
                .lastId(attractionDtoList.get(attractionDtoList.size()-1).getAttraction_id())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> findAttractionsByDistance(
            @RequestParam(defaultValue = DEFAULT_CURSOR_ID) Integer cursorId,
            @Valid @RequestBody LocationRequest locationRequest
            ) {
        BigDecimal latitude = locationRequest.getLatitude();
        BigDecimal longitude = locationRequest.getLongitude();
        List<AttractionNearByResponse> attractions = attractionService.findAttractionsByDistance(latitude, longitude, cursorId);

        AttractionNearByPagingResponse responseDto = AttractionNearByPagingResponse.builder()
                .attractions(attractions)
                .lastId(attractions.get(attractions.size()-1).getNo())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}

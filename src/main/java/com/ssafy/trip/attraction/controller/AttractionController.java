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
            @ModelAttribute AttractionSearchRequest searchRequest
    ) {
        List<Attraction> attractions = attractionService.findAttractionsByKeyword(
                searchRequest.getKeyword(),
                searchRequest.getCursorId(),
                searchRequest.getSpot(),
                searchRequest.getFacility(),
                searchRequest.getFestival(),
                searchRequest.getLeports(),
                searchRequest.getStay(),
                searchRequest.getShopping(),
                searchRequest.getRestaurant()
        );

        List<AttractionSearchResponse> attractionDtoList = attractions.stream()
                .map(attraction -> AttractionSearchResponse.builder()
                        .attractionId(attraction.getNo())
                        .title(attraction.getTitle())
                        .firstImage1(attraction.getFirstImage1())
                        .firstImage2(attraction.getFirstImage2())
                        .latitude(attraction.getLatitude())
                        .longitude((attraction.getLongitude()))
                        .address(attraction.getAddr1())
                        .build()
                )
                .collect(Collectors.toList());

        AttractionSearchPagingResponse responseDto = AttractionSearchPagingResponse.builder()
                .attractions(attractionDtoList)
                .lastId(attractionDtoList.get(attractionDtoList.size()-1).getAttractionId())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<AttractionNearByPagingResponse> findAttractionsByDistance(
            @RequestParam(defaultValue = DEFAULT_CURSOR_ID) Integer cursorId,
            @Valid @RequestBody LocationRequest locationRequest
            ) {
        BigDecimal latitude = locationRequest.getLatitude();
        BigDecimal longitude = locationRequest.getLongitude();
        List<AttractionNearByResponse> attractions = attractionService.findAttractionsByDistance(latitude, longitude, cursorId);

        AttractionNearByPagingResponse responseDto = AttractionNearByPagingResponse.builder()
                .attractions(attractions)
                .lastId(attractions.get(attractions.size()-1).getRow())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}

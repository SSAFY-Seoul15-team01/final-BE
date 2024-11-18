package com.ssafy.trip.attraction.controller;

import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.dto.AttractionResponse;
import com.ssafy.trip.attraction.dto.AttractionsResponse;
import com.ssafy.trip.attraction.dto.LocationRequest;
import com.ssafy.trip.attraction.service.AttractionService;
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
    public ResponseEntity<AttractionsResponse> findAttractionsByKeyword(
            @RequestParam @NotBlank String keyword,
            @RequestParam(defaultValue = DEFAULT_CURSOR_ID) Integer cursorId
    ) {
        List<Attraction> attractions = attractionService.findAttractionsByKeyword(keyword, cursorId);

        List<AttractionResponse> attractionDtoList = attractions.stream()
                .map(attraction -> AttractionResponse.builder()
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

        AttractionsResponse responseDto = AttractionsResponse.builder()
                .attractions(attractionDtoList)
                .lastId(attractionDtoList.get(attractionDtoList.size()-1).getAttraction_id())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> findAttractionsByDistance(
            @RequestParam(defaultValue = DEFAULT_CURSOR_ID) Integer cursorId,
            @RequestBody LocationRequest locationRequest
            ) {
        BigDecimal latitude = locationRequest.getLatitude();
        BigDecimal longitude = locationRequest.getLongitude();
        List<Attraction> attractions = attractionService.findAttractionsByDistance(latitude, longitude, cursorId);

        List<AttractionResponse> attractionDtoList = attractions.stream()
                .map(attraction -> AttractionResponse.builder()
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

        AttractionsResponse responseDto = AttractionsResponse.builder()
                .attractions(attractionDtoList)
                .lastId(attractionDtoList.get(attractionDtoList.size()-1).getAttraction_id())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}

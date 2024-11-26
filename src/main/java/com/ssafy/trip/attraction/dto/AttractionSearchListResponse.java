package com.ssafy.trip.attraction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class AttractionSearchListResponse {
    private List<AttractionSearchResponse> attractions;
}

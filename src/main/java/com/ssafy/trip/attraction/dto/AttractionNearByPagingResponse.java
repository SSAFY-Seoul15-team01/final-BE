package com.ssafy.trip.attraction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class AttractionNearByPagingResponse {
    private List<AttractionNearByResponse> attractions;
    private Integer lastId;
}

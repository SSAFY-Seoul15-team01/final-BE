package com.ssafy.trip.attraction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class AttractionsByKeywordResponse {
    private List<AttractionResponse> attractions;
    private Integer lastId;
}

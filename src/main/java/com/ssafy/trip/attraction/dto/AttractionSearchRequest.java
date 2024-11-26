package com.ssafy.trip.attraction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AttractionSearchRequest {
    @NotNull
    private String keyword;

    @NotNull
    private Boolean spot;

    @NotNull
    private Boolean facility;

    @NotNull
    private Boolean festival;

    @NotNull
    private Boolean leports;

    @NotNull
    private Boolean stay;

    @NotNull
    private Boolean shopping;

    @NotNull
    private Boolean restaurant;
}

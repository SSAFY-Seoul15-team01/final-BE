package com.ssafy.trip.attraction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AttractionSearchRequest {
    private static final Integer DEFAULT_CURSOR_ID = 0;

    @NotNull
    private String keyword;

    private Integer cursorId;

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

    public Integer getCursorId() {
        return cursorId != null ? cursorId : DEFAULT_CURSOR_ID;
    }
}

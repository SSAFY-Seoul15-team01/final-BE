package com.ssafy.trip.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    FAR_FROM_ATTRACTION(HttpStatus.BAD_REQUEST, "User Location is not near the attraction"),
    GEOLOCATION_IS_NULL(HttpStatus.BAD_REQUEST, "Geo location is null"),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member is not found"),
    ATTRACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Attraction is not found");

    private final HttpStatus status;
    private final String message;
}
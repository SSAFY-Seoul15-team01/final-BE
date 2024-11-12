package com.ssafy.trip.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member is not found"),
    ATTRACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Attraction is not found");

    private final HttpStatus status;
    private final String message;
}
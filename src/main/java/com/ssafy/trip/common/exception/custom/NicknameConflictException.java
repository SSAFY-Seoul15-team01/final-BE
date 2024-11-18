package com.ssafy.trip.common.exception.custom;

import com.ssafy.trip.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NicknameConflictException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public NicknameConflictException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}

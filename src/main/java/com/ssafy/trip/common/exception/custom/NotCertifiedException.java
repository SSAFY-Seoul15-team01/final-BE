package com.ssafy.trip.common.exception.custom;

import com.ssafy.trip.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotCertifiedException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public NotCertifiedException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}

package com.ssafy.trip.common.exception.custom;

import com.ssafy.trip.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class S3Exception extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public S3Exception(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}

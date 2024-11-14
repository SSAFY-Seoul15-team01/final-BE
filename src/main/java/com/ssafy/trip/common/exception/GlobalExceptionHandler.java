package com.ssafy.trip.common.exception;

import com.ssafy.trip.common.exception.custom.BadRequestException;
import com.ssafy.trip.common.exception.custom.NotCertifiedException;
import com.ssafy.trip.common.exception.custom.NotFoundException;
import com.ssafy.trip.common.exception.custom.S3Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
        ErrorResponse response = new ErrorResponse(e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(NotCertifiedException.class)
    public ResponseEntity<ErrorResponse> notCertifiedException(NotCertifiedException e) {
        ErrorResponse response = new ErrorResponse(e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<ErrorResponse> s3Exception(S3Exception e) {
        ErrorResponse response = new ErrorResponse(e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }
}

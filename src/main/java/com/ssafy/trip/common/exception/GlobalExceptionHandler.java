package com.ssafy.trip.common.exception;

import com.ssafy.trip.common.exception.custom.NotCertifiedException;
import com.ssafy.trip.common.exception.custom.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
}

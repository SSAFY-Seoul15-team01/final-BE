package com.ssafy.trip.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    IMAGE_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "Failed to process image metadata"),
    IMAGE_READ_ERROR(HttpStatus.BAD_REQUEST, "Failed to read image file"),
    IMAGE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "Failed to upload image file"),
    IMAGE_ORIGINALNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "Image original name is not exist"),
    IMAGE_CONVERT_ERROR(HttpStatus.BAD_REQUEST, "Failed to convert image file from multipart file"),

    FAR_FROM_ATTRACTION(HttpStatus.BAD_REQUEST, "User Location is not near the attraction"),
    GEOLOCATION_IS_NULL(HttpStatus.BAD_REQUEST, "Geo location is null"),

    MEMBER_NOT_MATCH(HttpStatus.BAD_REQUEST, "Request member does not match the session info"),

    INVALID_LIKE_ACTION(HttpStatus.BAD_REQUEST, "Like action is invalid"),
    INVALID_LOCATION(HttpStatus.BAD_REQUEST, "Location is invalid"),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member is not found"),
    ATTRACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Attraction is not found"),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND,  "Article is not found"),

    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "Nickname already exist");

    private final HttpStatus status;
    private final String message;
}
package com.ssafy.trip.common.exception.custom;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) { super(message); }
}

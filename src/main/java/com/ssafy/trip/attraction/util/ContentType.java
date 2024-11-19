package com.ssafy.trip.attraction.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    SPOT(12),
    FACILITY(14),
    FESTIVAL(15),
    LEPORTS(28),
    STAY(32),
    SHOPPING(38),
    RESTAURANT(39);

    private final Integer contentTypeId;
}

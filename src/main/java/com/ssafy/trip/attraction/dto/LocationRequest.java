package com.ssafy.trip.attraction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class LocationRequest {
    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}

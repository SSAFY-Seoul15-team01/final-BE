package com.ssafy.trip.auth.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RedirectResponse {

    private String code;
    private String state;
    private String error;
    private String error_description;
}

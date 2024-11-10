package com.ssafy.trip.auth.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizationResponse {
    private String resultCode;
    private String message;
    private UserInfoResponse response;
}

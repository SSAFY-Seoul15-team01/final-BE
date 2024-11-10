package com.ssafy.trip.auth.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoResponse {
    private String id;
    private String name;
    private String email;
    private String profileImage;
}

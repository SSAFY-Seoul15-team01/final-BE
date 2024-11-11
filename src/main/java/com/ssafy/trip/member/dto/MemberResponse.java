package com.ssafy.trip.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class MemberResponse {
    private Long id;
    private String nickname;
    private String profileUrl;
}
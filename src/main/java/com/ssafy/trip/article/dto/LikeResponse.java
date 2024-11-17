package com.ssafy.trip.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class LikeResponse {
    private Long likeCount;
}

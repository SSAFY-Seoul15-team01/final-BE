package com.ssafy.trip.article.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateArticleRequest {
    @NotNull
    private String content;

    @NotNull
    private Long memberId;

    @NotNull
    private Integer attractionId;
}
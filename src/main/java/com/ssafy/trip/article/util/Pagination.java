package com.ssafy.trip.article.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public enum Pagination {
    PAGE_SIZE(5);

    private final int value;

    public int calculateOffset(Integer pageNumber) {
        return (pageNumber - 1) * this.getValue();
    }
}

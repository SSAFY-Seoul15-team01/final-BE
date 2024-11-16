package com.ssafy.trip.article.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Pagination {
    DEFAULT_PAGE(1),
    PAGE_SIZE(5);

    private final int value;

    public int calculateOffset(Integer pageNumber) {
        return (pageNumber - 1) * this.getValue();
    }
}

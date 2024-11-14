package com.ssafy.trip.article.service;

import com.ssafy.trip.member.domain.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Long createArticle(String content, Long memberId, Integer attractionId, List<MultipartFile> images);
}

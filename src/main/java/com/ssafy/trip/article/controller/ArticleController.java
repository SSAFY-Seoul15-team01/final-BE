package com.ssafy.trip.article.controller;

import com.ssafy.trip.article.dto.CreateArticleRequest;
import com.ssafy.trip.article.dto.CreatedAtricleResponse;
import com.ssafy.trip.article.service.ArticleService;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<CreatedAtricleResponse> createArticle(
            @RequestPart List<MultipartFile> images,
            @RequestPart(value = "articleData") CreateArticleRequest createArticleRequest,
            HttpSession httpSession) {
        HashMap<String, Object> userInfo = (HashMap<String, Object>) httpSession.getAttribute("userInfo");

        if (!createArticleRequest.getMemberId().equals(userInfo.get("id"))) {
            throw new BadRequestException(ErrorCode.MEMBER_NOT_MATCH);
        }

        Long articleId = articleService.createArticle(
                createArticleRequest.getContent(),
                createArticleRequest.getMemberId(),
                createArticleRequest.getAttractionId(),
                images
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CreatedAtricleResponse.builder().id(articleId).build()
        );
    }
}
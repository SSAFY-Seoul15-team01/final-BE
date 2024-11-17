package com.ssafy.trip.article.controller;

import com.ssafy.trip.article.dto.*;
import com.ssafy.trip.article.service.ArticleService;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    private static final int MIN_PAGE = 1;
    private static final String MIN_PAGE_STR = "1";
    private static final long MAX_CURSOR_ID = Long.MAX_VALUE;
    private static final String MAX_CURSOR_ID_STR = "" + MAX_CURSOR_ID;

    @GetMapping("/members/{memberId}/sidos/{sidoCode}")
    public ResponseEntity<ArticleListWithLastIdResponse> getMemberCharacterArticle(
            @PathVariable Long memberId,
            @PathVariable Integer sidoCode,
            @RequestParam(defaultValue = MAX_CURSOR_ID_STR) @Min(MAX_CURSOR_ID) Long cursorId,
            HttpSession httpSession
    ) {
        HashMap<String, Object> userInfo = (HashMap<String, Object>) httpSession.getAttribute("userInfo");

        if (!memberId.equals(userInfo.get("id"))) {
            throw new BadRequestException(ErrorCode.MEMBER_NOT_MATCH);
        }

        List<ArticleResponse> articles = articleService.getArticlesOfMemberCharacter(memberId, sidoCode, cursorId);

        ArticleListWithLastIdResponse responseDto = ArticleListWithLastIdResponse.builder()
                .articles(articles)
                .lastId(articles.get(articles.size() - 1).getId())
                .build();

        return ResponseEntity.ok(responseDto);
    }

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

    @GetMapping
    public ResponseEntity<ArticleListResponse> getRecommendedArticles(
            @RequestParam(defaultValue = MIN_PAGE_STR) @Min(MIN_PAGE) @Valid Integer pageNumber
    ) {
        ArticleListResponse responseDto = ArticleListResponse.builder()
                .articles(articleService.getRecommendedArticles(pageNumber))
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
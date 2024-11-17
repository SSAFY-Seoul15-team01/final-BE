package com.ssafy.trip.article.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.trip.article.domain.Article;
import com.ssafy.trip.article.domain.ArticleImage;
import com.ssafy.trip.article.domain.Like;
import com.ssafy.trip.article.dto.ArticleResponse;
import com.ssafy.trip.article.repository.ArticleImageRepository;
import com.ssafy.trip.article.repository.ArticleRepository;
import com.ssafy.trip.article.repository.LikeRepository;
import com.ssafy.trip.article.util.Pagination;
import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionRepository;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final MemberRepository memberRepository;
    private final AttractionRepository attractionRepository;
    private final LikeRepository likeRepository;

    private final AmazonS3 s3Client;

    @Value("${aws-adk.bucketName}")
    private String bucketName;

    @Override
    public Long createArticle(String content, Long memberId, Integer attractionId, List<MultipartFile> images) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BadRequestException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Attraction attraction = attractionRepository.findByNo(attractionId).orElseThrow(() ->
                new BadRequestException(ErrorCode.ATTRACTION_NOT_FOUND)
        );

        Article article = articleRepository.save(
                Article.builder()
                        .content(content)
                        .member(member)
                        .attraction(attraction)
                        .build()
        );

        List<String> urlList = new ArrayList<>();

        for (MultipartFile image : images) {
            urlList.add(uploadImage(member.getNickname(), image));
        }

        List<ArticleImage> articleImageList = new ArrayList<>();

        for (String url : urlList) {
            articleImageList.add(
                    ArticleImage.builder()
                            .article(article)
                            .imageUrl(url)
                            .build()
            );
        }

        articleImageRepository.saveAll(articleImageList);

        return article.getId();
    }

    @Override
    public List<ArticleResponse> getRecommendedArticles(Integer pageNumber) {
        List<Tuple> articles = articleRepository.findRecommendArticles(
                Pagination.PAGE_SIZE.getValue(), Pagination.PAGE_SIZE.calculateOffset(pageNumber)
        );

        return articles.stream()
                .map(tuple -> {
                    Article article = tuple.get(0, Article.class);
                    Long likeCount = tuple.get(1, Long.class);
                    List<String> imageUrls = articleImageRepository.findImageUrlsByArticleId(article.getId());

                    return ArticleResponse.builder()
                            .id(article.getId())
                            .content(article.getContent())
                            .createdAt(article.getCreatedAt())
                            .imageUrls(imageUrls)
                            .likes(likeCount)
                            .memberId(article.getMember().getId())
                            .memberNickname(article.getMember().getNickname())
                            .build();
                })
                .toList();
    }

    @Override
    public List<ArticleResponse> getArticlesOfMemberCharacter(Long memberId, Integer sidoId, Long cursorId) {
        List<Tuple> articles = articleRepository.findArticlesByMemberAndSido(
                memberId, sidoId, cursorId, Pagination.PAGE_SIZE.getValue()
        );

        return getArticleResponses(articles);
    }

    @Override
    public List<ArticleResponse> getAtriclesOfAttraction(Integer attractionId, Long cursorId) {
        List<Tuple> articles = articleRepository.findArticlesByAttraction(
                attractionId, cursorId, Pagination.PAGE_SIZE.getValue()
        );

        return getArticleResponses(articles);
    }

    @Override
    public Long addLike(Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BadRequestException(ErrorCode.ARTICLE_NOT_FOUND)
        );
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BadRequestException(ErrorCode.MEMBER_NOT_FOUND)
        );

        if (likeRepository.existsByMemberAndArticle(member, article)) {
            throw new BadRequestException(ErrorCode.INVALID_LIKE_ACTION);
        }

        likeRepository.save(Like.builder()
                .article(article)
                .member(member)
                .build());

        return likeRepository.countByArticle(article);
    }

    @Override
    public Long removeLike(Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BadRequestException(ErrorCode.ARTICLE_NOT_FOUND)
        );
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BadRequestException(ErrorCode.MEMBER_NOT_FOUND)
        );

        Like like = likeRepository.findByMemberAndArticle(member, article).orElseThrow(() ->
                new BadRequestException(ErrorCode.INVALID_LIKE_ACTION));
        likeRepository.delete(like);

        return likeRepository.countByArticle(article);
    }

    private String uploadImage(String nickname, MultipartFile imageFile) {
        File uploadedFile = convert(imageFile);
        return uploadImage(nickname, uploadedFile);
    }

    private String uploadImage(String nickname, File imageFile) {
        String fileName = nickname + "/" + imageFile.getName();

        s3Client.putObject(
                new PutObjectRequest(bucketName, fileName, imageFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        imageFile.delete();

        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private File convert(MultipartFile imageFile) {
        String filename = UUID.randomUUID().toString().substring(0, 10)
                + "_" + imageFile.getOriginalFilename();
        File convertFile = new File(filename);

        try {
            if (!convertFile.exists()) {
                convertFile.createNewFile();
            }

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(imageFile.getBytes());
            }
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.IMAGE_READ_ERROR);
        }
        return convertFile;
    }

    private List<ArticleResponse> getArticleResponses(List<Tuple> articles) {
        return articles.stream()
                .map(tuple -> {
                    Long articleId = tuple.get("articleId", Long.class);
                    List<String> imageUrls = articleImageRepository.findImageUrlsByArticleId(articleId);

                    return ArticleResponse.builder()
                            .id(articleId)
                            .content(tuple.get("articleContent", String.class))
                            .createdAt(tuple.get("articleCreatedAt", LocalDateTime.class))
                            .imageUrls(imageUrls)
                            .likes(tuple.get("likes", Long.class))
                            .memberId(tuple.get("memberId", Long.class))
                            .memberNickname(tuple.get("memberNickname", String.class))
                            .build();
                })
                .toList();
    }
}

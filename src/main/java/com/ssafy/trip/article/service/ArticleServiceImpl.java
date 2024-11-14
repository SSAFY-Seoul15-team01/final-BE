package com.ssafy.trip.article.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.trip.article.domain.Article;
import com.ssafy.trip.article.domain.ArticleImage;
import com.ssafy.trip.article.repository.ArticleImageRepository;
import com.ssafy.trip.article.repository.ArticleRepository;
import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionRepository;
import com.ssafy.trip.common.config.S3Config;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import com.ssafy.trip.common.exception.custom.NotFoundException;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final MemberRepository memberRepository;
    private final AttractionRepository attractionRepository;

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
}

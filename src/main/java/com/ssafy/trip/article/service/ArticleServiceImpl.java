package com.ssafy.trip.article.service;

import com.ssafy.trip.article.domain.Article;
import com.ssafy.trip.article.domain.ArticleImage;
import com.ssafy.trip.article.repository.ArticleImageRepository;
import com.ssafy.trip.article.repository.ArticleRepository;
import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionRepository;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.NotFoundException;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final MemberRepository memberRepository;
    private final AttractionRepository attractionRepository;

    @Override
    public Long createArticle(String content, Long memberId, Integer attractionId, List<MultipartFile> images) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Attraction attraction = attractionRepository.findById(attractionId).orElse(null);

        if (member == null) {
            throw new NotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        } else if (attraction == null) {
            throw new NotFoundException(ErrorCode.ATTRACTION_NOT_FOUND);
        }

        Article article = articleRepository.save(
                Article.builder()
                        .content(content)
                        .member(member)
                        .attraction(attraction)
                        .build()
        );

        List<String> urlList = uploadImage(member, images);
        List<ArticleImage> articleImageList = new ArrayList<>();

        for (String url : urlList) {
            articleImageList.add(
                    ArticleImage.builder()
                            .article(article)
                            .imageUrl(url)
                            .build());
        }

        articleImageRepository.saveAll(articleImageList);

        return article.getId();
    }

    @Override
    public List<String> uploadImage(Member member, List<MultipartFile> images) {
        List<String> urlList = new ArrayList<>();

        // Object storage

        return urlList;
    }


}

package com.ssafy.trip.auth.service;

import com.ssafy.trip.auth.domain.CustomOAuth2User;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
        String socialId = responseMap.get("id");

        Member member = null;

        if (!memberRepository.existsBySocialId(socialId)) {
            member = Member.builder()
                    .socialId(socialId)
                    .nickname("User_" + socialId)
                    .socialType(oauthClientName)
                    .build();

            memberRepository.save(member);
        } else {
            member = memberRepository.findBySocialId(socialId);
        }

        httpSession.setAttribute("userInfo", member.getNickname());

        return new CustomOAuth2User(socialId);
    }
}

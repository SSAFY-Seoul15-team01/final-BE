package com.ssafy.trip.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.auth.domain.CustomOAuth2User;
import com.ssafy.trip.auth.domain.UserPrincipal;
import com.ssafy.trip.auth.dto.OAuthAttributes;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
        String socialId = responseMap.get("id");
        Member member = Member.builder()
                .socialId(socialId)
                .nickname("User_" + socialId)
                .socialType("naver")
                .build();
        memberRepository.save(member);

//        String registrationId = request.getClientRegistration().getRegistrationId();
//        String userNameAttributeName = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CustomOAuth2User(socialId);
    }


//    private final MemberRepository memberRepository;
//
//    public CustomOAuth2UserService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest OAuth2UserRequest) {
//        Map<String, Object> attributes = super.loadUser(OAuth2UserRequest).getAttributes();
//
//        validationAttributes(attributes);
//
//        Member member = registerIfNewMember(attributes);
//
//        return UserPrincipal.create(member, attributes);
//    }
//
//    private Member registerIfNewMember(Map<String, Object> userInfoAttributes) {
//        String socialId = (String) userInfoAttributes.get("socialId");
//        Optional<Member> optionalMember = Optional.ofNullable(memberRepository.findBySocialId(socialId));
//        if (optionalMember.isPresent()) {
//            return optionalMember.get();
//        }
//        Member member = Member.builder()
//                .socialId(socialId)
//                .nickname("User_"+socialId)
//                .build();
//        return memberRepository.save(member);
//    }
//
//    private void validationAttributes(Map<String, Object> userInfoAttributes) {
//        if (userInfoAttributes.containsKey("social_id")) {
//            throw new IllegalArgumentException("social id attribute is required");
//        }
//    }

}

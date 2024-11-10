package com.ssafy.trip.auth.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.ssafy.trip.auth.dto.response.AuthorizationResponse;
import com.ssafy.trip.auth.dto.response.RedirectResponse;
import com.ssafy.trip.auth.dto.response.TokenResponse;
import com.ssafy.trip.auth.dto.response.UserInfoResponse;
import com.ssafy.trip.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@Service
@Transactional
public class AuthService {
    private final MemberRepository memberRepository;
    private final WebClient webClient;

    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;
    private String callBackUrl = "http://localhost:8080/auth/redirect";

    public AuthService(WebClient.Builder webClientBuilder, MemberRepository memberRepository) {
        this.webClient = webClientBuilder.baseUrl("https://nid.naver.com/oauth2.0").build();
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<Void> authorize() throws URISyntaxException {
        String state = "test";
        String url = "https://nid.naver.com/oauth2.0" +
                "/authorize" +
                "?response_type=code" +
                "&client_id=" +
                clientId +
                "&state=" +
                state +
                "&redirect_uri=" +
                callBackUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    public ResponseEntity<Object> getUserInfo(String code, String state) throws URISyntaxException {
        System.out.println("리다이렉트 들어옴");
        TokenResponse tokenResponse = getAccessToken(code, state).block();
        String AccessToken = tokenResponse.getAccessToken();
        String uri = "https://openapi.naver.com/v1/nid/me";
        UserInfoResponse response = webClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + AccessToken)
                .retrieve()
                .bodyToMono(AuthorizationResponse.class)
                .block()
                .getResponse();

        return null;
    }

    public void login(String socialId) {
        if (!isUserExist(socialId)) {
            join(socialId);
        }
    }

    public void join(String socialId) {

    }

    public boolean isUserExist(String socialId) {
        return memberRepository.existsBySocialId(socialId);
    }

    public Mono<TokenResponse> getAccessToken(String code, String state) throws URISyntaxException {
        String uri = "https://nid.naver.com/oauth2.0/token" +
                "?grant_type=authorization_code" +
                "&client_id=" +
                clientId +
                "&client_secret=" +
                clientSecret +
                "&code=" +
                code +
                "&state=" +
                state;
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }

//    public TokenResponse getRefreshToken(String refreshToken) throws URISyntaxException {
//        String uri = baseUrl +
//                "?grant_type=refresh_token" +
//                "&client_id=" +
//                CLIENT_ID +
//                "&client_secret=" +
//                CLIENT_SECRET +
//                "refresh_token=" +
//                refreshToken;
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application", "json"));
//        return restTemplate.postForObject(uri, httpHeaders, TokenResponse.class);
//    }
//
//    public TokenResponse deleteToken(String accessToken) throws URISyntaxException {
//        String uri = baseUrl +
//                "?grant_type=delete" +
//                "&client_id=" +
//                CLIENT_ID +
//                "&client_secret=" +
//                CLIENT_SECRET +
//                "refresh_token=" +
//                accessToken;
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application", "json"));
//        return restTemplate.postForObject(uri, httpHeaders, TokenResponse.class);
//    }

}

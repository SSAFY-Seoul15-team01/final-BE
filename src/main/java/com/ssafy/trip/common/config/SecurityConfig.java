package com.ssafy.trip.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configurable
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final DefaultOAuth2UserService defaultOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .logout((logout) ->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .oauth2Login((oauth2) -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/auth/login"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/auth/redirect/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(defaultOAuth2UserService)));

        return http.build();
    }

}

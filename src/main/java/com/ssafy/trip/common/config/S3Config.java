package com.ssafy.trip.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws-sdk")
@Getter
public class S3Config {
    private String endpoint;
    private String bucketname;
    private String accesskey;
    private String secretkey;
}

package com.study.spring.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "components.egoism")
public class Egoism {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public Egoism setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}

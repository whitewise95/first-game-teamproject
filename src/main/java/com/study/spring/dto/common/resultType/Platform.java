package com.study.spring.dto.common.resultType;

import static com.study.spring.dto.common.resultType.Platform.Const.*;

public enum Platform {
    GOOGLE(GOOGLE_NAME),
    FACEBOOK(FACEBOOK_NAME);

    private String platform;

    Platform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public Platform setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public static class Const {
        public static final String FACEBOOK_NAME = "facebook";
        public static final String GOOGLE_NAME = "google";
    }
}

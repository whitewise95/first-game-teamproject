package com.study.spring.components;

public class DefaultData {
    private String secretKey;
    private String fireBaseKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getFireBaseKey() {
        return fireBaseKey;
    }

    public DefaultData setFireBaseKey(String fireBaseKey) {
        this.fireBaseKey = fireBaseKey;
        return this;
    }
}

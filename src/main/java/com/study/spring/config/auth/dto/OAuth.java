package com.study.spring.config.auth.dto;

import lombok.*;

@Getter
@Setter
public class OAuth {
    private String userInfo;
    private String uid;

    public OAuth() {
    }

    public OAuth(String userInfo, String uid) {
        this.userInfo = userInfo;
        this.uid = uid;
    }
}

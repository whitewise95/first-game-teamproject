package com.study.spring.config.auth.dto;

import com.study.spring.domain.User;
import lombok.*;

@Getter
@Setter
public class OAuth {
    private String uniqueNumber;
    private String uid;
    private String platform;
}

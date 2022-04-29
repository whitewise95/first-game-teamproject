package com.study.spring.dto;

import com.study.spring.domain.Login;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class OAuth {
    @NotEmpty(groups = { Login.class }, message = "platform 는 반드시 존재해야 합니다.")
    private String uniqueNumber;
    private String uid;
    @NotEmpty(groups = { Login.class }, message = "platform 는 반드시 존재해야 합니다.")
    private String platform;

    @Builder
    public OAuth(String uniqueNumber, String uid, String platform) {
        this.uniqueNumber = uniqueNumber;
        this.uid = uid;
        this.platform = platform;
    }
}

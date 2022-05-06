package com.study.spring.dto;

import com.study.spring.domain.resultType.Login;
import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class OAuth {
    @NotEmpty(groups = { Login.class }, message = "platform 는 반드시 존재해야 합니다.")
    private String uniqueNumber;
    @NotEmpty(groups = { Login.class }, message = "platform 는 반드시 존재해야 합니다.")
    private String token;
    private String uid;
    private String platform;

    @Builder
    public OAuth(String uniqueNumber, String uid) {
        this.uniqueNumber = uniqueNumber;
        this.uid = uid;
    }
}

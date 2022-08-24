package com.study.spring.exceptionHandler.CustumException;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //믹스매치
    FAIL_DATABASE_SAVE(5050, 500, "데이터베이스에 저장하지 못했습니다.");

    private final int statusCode;
    private final int realStatusCode;
    private final String message;
}

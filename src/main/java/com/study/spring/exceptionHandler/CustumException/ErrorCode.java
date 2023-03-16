package com.study.spring.exceptionHandler.CustumException;

import lombok.*;

@Getter
public enum ErrorCode {

    //데이터베이스
    FAIL_DATABASE_SAVE(5050, 500, "데이터베이스에 저장하지 못했습니다."),
    FAIL_DATABASE_FIND(5050, 500, "데이터베이스에서 조회하지 못했습니다."),

    //찾지 못했습니다.
    NOT_FIND_USER(4040, 500, "유저를 찾지 못했습니다.");

    private int statusCode;
    private int realStatusCode;
    private String message;

    ErrorCode(int statusCode, int realStatusCode, String message) {
        this.statusCode = statusCode;
        this.realStatusCode = realStatusCode;
        this.message = message;
    }
}

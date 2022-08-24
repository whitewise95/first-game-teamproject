package com.study.spring.exceptionHandler.CustumException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private String message;
    private int statusCode;

    @JsonIgnore
    private int realStatusCode;

    public CustomException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.statusCode = errorCode.getStatusCode();
        this.realStatusCode = errorCode.getRealStatusCode();
    }

    public CustomException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}

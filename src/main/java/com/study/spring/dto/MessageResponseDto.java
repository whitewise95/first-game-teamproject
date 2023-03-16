package com.study.spring.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class MessageResponseDto<T> {
    private int statusCode;
    private String message;
    private T content;

    public MessageResponseDto(int statusCode, String message, T content) {
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
    }

    public MessageResponseDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}

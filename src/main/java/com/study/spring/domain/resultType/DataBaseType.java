package com.study.spring.domain.resultType;

import lombok.Getter;

@Getter
public enum DataBaseType {
    DEFAULT_DATABASE("https://Egoism.firebaseio.com/"),
    REAL_TIME_DATABASE("https://egoism-3af0d-default-rtdb.firebaseio.com/");

    private String type;

    DataBaseType(String type) {
        this.type = type;
    }
}

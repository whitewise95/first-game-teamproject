package com.study.spring.domain.resultType;

import lombok.Getter;

@Getter
public enum DataBaseType {
    DEFAULT_DATABASE("https://Egoism.firebaseio.com/", "[DEFAULT]"),
    REAL_TIME_DATABASE("https://egoism-3af0d-default-rtdb.firebaseio.com/", "real");

    private String type;
    private String key;

    DataBaseType(String type, String key) {
        this.type = type;
        this.key = key;
    }
}

package com.study.spring.dto.common.resultType;

public enum DataBaseTable {
    USER("user"),
    SOCIAL("social");

    private String table;

    DataBaseTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public DataBaseTable setTable(String table) {
        this.table = table;
        return this;
    }
}

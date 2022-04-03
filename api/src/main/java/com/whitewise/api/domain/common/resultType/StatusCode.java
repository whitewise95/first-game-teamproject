package com.whitewise.api.domain.common.resultType;

public enum StatusCode {
    OK(2000),
    DUPLICATE(4000),
    NO_CONTENT(4040);

    private int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}

package com.whitewise.web.model;

public class HelloResponseDto {

    private String name;
    private int amount;

    public HelloResponseDto(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public HelloResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public HelloResponseDto setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}

package com.study.spring.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class WaitRequestDto {
    private List<CardDto> cardList;
    private String uid;
    private int currentCustomNum;

    static public class CardDto {
        private String coordinate;

        public String getCoordinate() {
            return coordinate;
        }
    }
}

package com.study.spring.domain;

import com.study.spring.dto.WaitRequestDto;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class CardCoordinate {
    private String coordinate;

    public CardCoordinate(WaitRequestDto.CardDto coordinate) {
        this.coordinate = coordinate.getCoordinate();
    }
}

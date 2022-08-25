package com.study.spring.dto;

import com.study.spring.domain.valid.WaitValidGroups;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class WaitRequestDto {

    @Valid
    private List<CardDto> cardList;

    @NotNull(groups = { WaitValidGroups.Card.class, WaitValidGroups.Custom.class }, message = "uid는 반드시 존재해야합니다.")
    private String uid;

    @Min(value = 1, groups = WaitValidGroups.Custom.class, message = "currentCustomNum는 최소 1 이상 최대 10이하 입니다. ")
    @Max(value = 10, groups = WaitValidGroups.Custom.class, message = "currentCustomNum는 최소 1 이상 최대 10이하 입니다. ")
    private int currentCustomNum;

    static public class CardDto {

        @NotNull(groups = { WaitValidGroups.Card.class }, message = "coordinate는 반드시 존재해야합니다.")
        private String coordinate;

        public String getCoordinate() {
            return coordinate;
        }
    }
}

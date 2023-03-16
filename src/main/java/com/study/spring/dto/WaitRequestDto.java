package com.study.spring.dto;

import com.study.spring.domain.valid.WaitValidGroups;
import lombok.*;

import javax.validation.constraints.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WaitRequestDto {

    @Size(groups = WaitValidGroups.Card.class, message = "cardList 는 10이하로만 설정가능합니다.", max = 10)
    private List<String> cardList;

    @NotNull(groups = { WaitValidGroups.Card.class, WaitValidGroups.Custom.class }, message = "uid는 반드시 존재해야합니다.")
    private String uid;

    @Min(value = 1, groups = WaitValidGroups.Custom.class, message = "currentCustomNum는 최소 1 이상 최대 10이하 입니다. ")
    @Max(value = 10, groups = WaitValidGroups.Custom.class, message = "currentCustomNum는 최소 1 이상 최대 10이하 입니다. ")
    private int currentCustomNum;
}

package com.study.spring.domain;

import com.study.spring.domain.resultType.NickName;
import lombok.*;

import javax.validation.constraints.NotEmpty;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {
    @NotEmpty(groups = { NickName.class }, message = "uid는 반드시 존재해야합니다.")
    private String uid;
    private String email;
    private float exp;
    private int gold;
    private int level;
    private int cardPiece;
    @NotEmpty(groups = { NickName.class }, message = "nickName 반드시 존재해야합니다.")
    private String nickName;
    private int normalItem;
    private int luxuryItem;
    private List<Card> cardList;
    private List<Costume> costumes;

    @Builder
    public User(String uid, String email, String nickName) {
        this.uid = uid;
        this.email = email;
        this.nickName = nickName;
    }

    @Getter
    @Setter
    public class Card {
        private int number;
        private String skillName;
        private int cardLevel;
        private int cardExp;
    }

    @Getter
    @Setter
    public class Costume {
        private	int	number;
    }
}

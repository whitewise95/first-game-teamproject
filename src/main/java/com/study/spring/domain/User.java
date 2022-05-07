package com.study.spring.domain;

import com.study.spring.domain.resultType.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class User {
    @NotEmpty(groups = { NickName.class , Login.class }, message = "uid는 반드시 존재해야합니다.")
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
    private List<Card> cardList = new ArrayList<>();
    private List<Costume> costumes = new ArrayList<>();

    @Builder
    public User(String uid, String email, String nickName) {
        this.uid = uid;
        this.email = email;
        this.nickName = nickName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Card {
        private int number;
        private String skillName;
        private int cardLevel;
        private int cardExp;

        @Builder
        public Card(int number, String skillName, int cardLevel, int cardExp) {
            this.number = number;
            this.skillName = skillName;
            this.cardLevel = cardLevel;
            this.cardExp = cardExp;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Costume {
        private	int	number;

        public Costume(int number) {
            this.number = number;
        }
    }
}

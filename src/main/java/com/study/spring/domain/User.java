package com.study.spring.domain;

import com.study.spring.domain.resultType.*;
import lombok.*;

import javax.validation.constraints.*;

import java.util.*;

@Data
@NoArgsConstructor
public class User {
    @NotEmpty(groups = { NickName.class, Login.class, Update.class }, message = "uid는 반드시 존재해야합니다.")
    private String uid;
    private String email;
    @NotNull(groups = { Update.class }, message = "exp 반드시 존재해야합니다.")
    private float exp;
    @NotNull(groups = { Update.class }, message = "gold 반드시 존재해야합니다.")
    private int gold;
    @NotNull(groups = { Update.class }, message = "level 반드시 존재해야합니다.")
    private int level;
    private int cardPiece;
    @NotEmpty(groups = { NickName.class }, message = "nickName 반드시 존재해야합니다.")
    private String nickName;
    private int normalItem;
    private int luxuryItem;
    private List<Card> cardList = new ArrayList<>();
    private List<Costume> costumeList = new ArrayList<>();

    @Builder
    public User(String uid, String email, String nickName, int level) {
        this.uid = uid;
        this.email = email;
        this.nickName = nickName;
        this.level = level;
    }

    @Data
    @NoArgsConstructor
    public static class Card {
        private int number;
        private String skillName;
        private int cardLevel;
        private int cardExp;

        @Builder
        public Card(int number, String skillName, int cardExp, int cardLevel) {
            this.number = number;
            this.skillName = skillName;
            this.cardLevel = cardLevel;
            this.cardExp = cardExp;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Costume {
        private int number;

        public Costume(int number) {
            this.number = number;
        }
    }
}

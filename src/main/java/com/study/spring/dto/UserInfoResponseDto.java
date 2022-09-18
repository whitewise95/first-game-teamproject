package com.study.spring.dto;

import com.study.spring.domain.User;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private String uid;
    private String email;
    private float exp;
    private int gold;
    private int level;
    private int cardPiece;
    private String nickName;
    private int normalItem;
    private int luxuryItem;
    private List<User.Card> cardList;
    private List<User.Costume> costumeList;
    private List<String> humanList;
    private List<String> hunterList;
    private int currentCustomNum;

    public void setUser(User user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.exp = user.getExp();
        this.gold = user.getGold();
        this.level = user.getLevel();
        this.cardPiece = user.getCardPiece();
        this.nickName = user.getNickName();
        this.normalItem = user.getNormalItem();
        this.luxuryItem = user.getLuxuryItem();
        this.cardList = user.getCardList();
        this.costumeList = user.getCostumeList();
    }
}

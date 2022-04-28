package com.study.spring.domain;

import lombok.*;

@Getter
@Setter
public class User {
    private String uid;
    private float exp;
    private int cold;
    private int level;
    private int cardPiece;
    private int normalItem;
    private int luxuryItem;
}

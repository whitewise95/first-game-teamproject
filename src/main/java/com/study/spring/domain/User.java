package com.study.spring.domain;

import com.google.firebase.auth.UserRecord;
import com.study.spring.domain.user.Role;

import javax.persistence.*;

public class User extends UserRecord.CreateRequest {
    private String uid;
    private float exp;
    private int cold;
    private int level;
    private int cardPiece;
    private int normalItem;
    private int luxuryItem;

    // UserRecord.CreateRequest
    private String displayName;
    private boolean emailVerified;
    private String email;
    private String password;
    private boolean disabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User() {
    }

    public User(UserRecord user) {
        this.uid = user.getUid();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
    }

    public String getUid() {
        return uid;
    }

    @Override
    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public float getExp() {
        return exp;
    }

    public User setExp(float exp) {
        this.exp = exp;
        return this;
    }

    public int getCold() {
        return cold;
    }

    public User setCold(int cold) {
        this.cold = cold;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public User setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getCardPiece() {
        return cardPiece;
    }

    public User setCardPiece(int cardPiece) {
        this.cardPiece = cardPiece;
        return this;
    }

    public int getNormalItem() {
        return normalItem;
    }

    public User setNormalItem(int normalItem) {
        this.normalItem = normalItem;
        return this;
    }

    public int getLuxuryItem() {
        return luxuryItem;
    }

    public User setLuxuryItem(int luxuryItem) {
        this.luxuryItem = luxuryItem;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    @Override
    public User setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public User setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

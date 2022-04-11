package com.study.spring.domain;

import com.google.firebase.auth.UserRecord;

public class User extends UserRecord.CreateRequest {
    private String displayName;
    private boolean emailVerified;
    private String email;
    private String password;
    private String uid;
    private boolean disabled;

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

    public String getUid() {
        return uid;
    }

    @Override
    public User setUid(String uid) {
        this.uid = uid;
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
}

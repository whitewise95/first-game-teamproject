package com.study.spring.components;

public class FireBaseProperties {

    private String secretKey;
    private String fireBaseKey;
    private String databaseUrl;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getFireBaseKey() {
        return fireBaseKey;
    }

    public FireBaseProperties setFireBaseKey(String fireBaseKey) {
        this.fireBaseKey = fireBaseKey;
        return this;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public FireBaseProperties setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }
}

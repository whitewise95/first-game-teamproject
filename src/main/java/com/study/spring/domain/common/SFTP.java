package com.study.spring.domain.common;

public class SFTP {
    private String host;
    private String userName;
    private String password;
    private String baseUploadPath;
    private int port;

    public String getHost() {
        return host;
    }

    public SFTP setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SFTP setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SFTP setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getBaseUploadPath() {
        return baseUploadPath;
    }

    public SFTP setBaseUploadPath(String baseUploadPath) {
        this.baseUploadPath = baseUploadPath;
        return this;
    }

    public int getPort() {
        return port;
    }

    public SFTP setPort(int port) {
        this.port = port;
        return this;
    }
}

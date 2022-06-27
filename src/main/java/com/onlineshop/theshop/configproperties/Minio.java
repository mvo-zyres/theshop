package com.onlineshop.theshop.configproperties;

public class Minio {
    private int port;

    private int apiPort;
    private String innerHostname;
    private String outerHostname;
    private String bucket;
    private String user;
    private String password;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getApiPort() {
        return apiPort;
    }

    public void setApiPort(int apiPort) {
        this.apiPort = apiPort;
    }

    public String getInnerHostname() {
        return innerHostname;
    }

    public void setInnerHostname(String innerHostname) {
        this.innerHostname = innerHostname;
    }

    public String getOuterHostname() {
        return outerHostname;
    }

    public void setOuterHostname(String outerHostname) {
        this.outerHostname = outerHostname;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

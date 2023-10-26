package com.ttonline.vestman.models;

public class ResetPassRequest {
    private String password;

    public ResetPassRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

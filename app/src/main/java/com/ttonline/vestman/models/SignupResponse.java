package com.ttonline.vestman.models;

import java.util.ArrayList;

public class SignupResponse {
    private boolean success;
    private ClientModel data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ClientModel getData() {
        return data;
    }

    public void setData(ClientModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

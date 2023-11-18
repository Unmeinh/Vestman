package com.ttonline.vestman.models;

public class ChatbotResponse {
    private boolean success;
    private ChatbotModel data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ChatbotModel getData() {
        return data;
    }

    public void setData(ChatbotModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

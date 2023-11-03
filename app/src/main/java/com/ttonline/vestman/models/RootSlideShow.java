package com.ttonline.vestman.models;

import java.util.ArrayList;

public class RootSlideShow {
    public boolean success;
    public ArrayList<ModelSlideShow> data;
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<ModelSlideShow> getData() {
        return data;
    }

    public void setData(ArrayList<ModelSlideShow> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

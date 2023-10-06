package com.ttonline.vestman.models;

import java.util.ArrayList;

public class RootBill {
    public boolean success;
    public ArrayList<BillModel> data;
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<BillModel> getData() {
        return data;
    }

    public void setData(ArrayList<BillModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

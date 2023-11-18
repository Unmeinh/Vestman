package com.ttonline.vestman.models;

import java.io.Serializable;

public class CustomerInfo implements Serializable {
    String nameCustomer,phoneNumber,address;

    public CustomerInfo(String nameCustomer, String phoneNumber, String address) {
        this.nameCustomer = nameCustomer;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

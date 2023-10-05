package com.ttonline.vestman.models;

import java.util.Date;

public class ClientUpdateModel {
    public String _id,full_name,email,address;
    public Number phone_number;


    public ClientUpdateModel(String full_name, String email, String address, Number phone_number) {
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Number getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Number phone_number) {
        this.phone_number = phone_number;
    }

}

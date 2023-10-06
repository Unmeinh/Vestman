package com.ttonline.vestman.models;

import java.util.Date;

public class ClientModel {
    public String _id,username,password,full_name,email,address,avatar;
    public Number phone_number;
    public Date create_at;

    public ClientModel(String username, String password, String full_name, String email, String address, String avatar, Number phone_number, Date create_at) {
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        this.phone_number = phone_number;
        this.create_at = create_at;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
}

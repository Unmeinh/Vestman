package com.ttonline.vestman.models;

import java.util.Date;

public class Datum{
    public String _id;
    public String id_client;
    public IdProduct id_product;
    public String quantity;
    public String size;
    public Date created_at;


    public int __v;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public IdProduct getId_product() {
        return id_product;
    }

    public void setId_product(IdProduct id_product) {
        this.id_product = id_product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
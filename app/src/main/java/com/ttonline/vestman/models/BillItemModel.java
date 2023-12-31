package com.ttonline.vestman.models;


import java.util.ArrayList;
import java.util.Objects;

public class BillItemModel {
    private String _id,size;
    private int quantity,price;
    private IdProduct id_product;

    public BillItemModel(String size, int quantity, int price, IdProduct id_product) {
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.id_product = id_product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public IdProduct getId_product() {
        return id_product;
    }

    public void setId_product(IdProduct id_product) {
        this.id_product = id_product;
    }
}

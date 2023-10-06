package com.ttonline.vestman.models;

import java.util.ArrayList;
import java.util.Date;

public class BillModel {
    public String _id,id_client;
    public ArrayList<BillItemModel> arr_product;
    public Number total;
    public Date create_at;

    public BillModel(String id_client, ArrayList<BillItemModel> arr_product, Number total, Date create_at) {
        this.id_client = id_client;
        this.arr_product = arr_product;
        this.total = total;
        this.create_at = create_at;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<BillItemModel> getArr_product() {
        return arr_product;
    }

    public void setArr_product(ArrayList<BillItemModel> arr_product) {
        this.arr_product = arr_product;
    }

    public Number getTotal() {
        return total;
    }

    public void setTotal(Number total) {
        this.total = total;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
}

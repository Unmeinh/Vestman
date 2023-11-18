package com.ttonline.vestman.models;

import java.util.ArrayList;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BillModel {
    private String _id,id_client;
    private ArrayList<BillItemModel> arr_product;
    private int total;
    private String created_at;
    private int status;

    public BillModel(String id_client, ArrayList<BillItemModel> arr_product, int total, String created_at, int status) {
        this.id_client = id_client;
        this.arr_product = arr_product;
        this.total = total;
        this.created_at = created_at;
        this.status = status;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

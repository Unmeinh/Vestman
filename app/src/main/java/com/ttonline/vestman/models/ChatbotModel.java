package com.ttonline.vestman.models;

import java.util.ArrayList;
import java.util.Date;

public class ChatbotModel {
    private String _id,id_product;
    private ArrayList questions,replies;
    private Date create_at;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public ArrayList getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList questions) {
        this.questions = questions;
    }

    public ArrayList getReplies() {
        return replies;
    }

    public void setReplies(ArrayList replies) {
        this.replies = replies;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
}

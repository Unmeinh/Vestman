package com.ttonline.vestman.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class IdProduct implements Parcelable {
    public String _id;
    public ArrayList<Integer> sizes;
    public ArrayList<String> images;
    public String name_product;
    public String detail_product;
    public String id_discount;

    public String color;

    public int quantity;
    public int quantitySold;
    public int price;
    public Date created_at;
    public int __v;

    protected IdProduct(Parcel in) {
        _id = in.readString();
        images = in.createStringArrayList();
        name_product = in.readString();
        detail_product = in.readString();
        id_discount = in.readString();
        color = in.readString();
        quantity = in.readInt();
        quantitySold = in.readInt();
        price = in.readInt();
        __v = in.readInt();
    }

    public static final Creator<IdProduct> CREATOR = new Creator<IdProduct>() {
        @Override
        public IdProduct createFromParcel(Parcel in) {
            return new IdProduct(in);
        }

        @Override
        public IdProduct[] newArray(int size) {
            return new IdProduct[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<Integer> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<Integer> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getDetail_product() {
        return detail_product;
    }

    public void setDetail_product(String detail_product) {
        this.detail_product = detail_product;
    }

    public String getId_discount() {
        return id_discount;
    }

    public void setId_discount(String id_discount) {
        this.id_discount = id_discount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeStringList(images);
        dest.writeString(name_product);
        dest.writeString(detail_product);
        dest.writeString(id_discount);
        dest.writeString(color);
        dest.writeInt(quantity);
        dest.writeInt(quantitySold);
        dest.writeInt(price);
        dest.writeInt(__v);
    }
}

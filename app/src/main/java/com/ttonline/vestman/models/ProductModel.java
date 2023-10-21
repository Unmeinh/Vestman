package com.ttonline.vestman.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

//public class ProductModel implements Parcelable {
//    public String _id;
//    public ArrayList<Object> sizes;
//    public ArrayList<String> images;
//    public String name_product;
//    public String detail_product;
//    public IdDiscount id_discount;
//    public String color;
//    public int quantity;
//    public int quantitySold;
//    public int price;
//    public Date created_at;
//    public int __v;
//
//    public ProductModel(String name_product, int price) {
//        this.name_product = name_product;
//        this.price = price;
//    }
//
//    protected ProductModel(Parcel in) {
//        _id = in.readString();
//        images = in.createStringArrayList();
//        name_product = in.readString();
//        detail_product = in.readString();
//        color = in.readString();
//        quantity = in.readInt();
//        quantitySold = in.readInt();
//        price = in.readInt();
//        __v = in.readInt();
//    }
//
//    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
//        @Override
//        public ProductModel createFromParcel(Parcel in) {
//            return new ProductModel(in);
//        }
//
//        @Override
//        public ProductModel[] newArray(int size) {
//            return new ProductModel[size];
//        }
//    };
//
//    public String get_id() {
//        return _id;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }
//
//    public ArrayList<Object> getSizes() {
//        return sizes;
//    }
//
//    public void setSizes(ArrayList<Object> sizes) {
//        this.sizes = sizes;
//    }
//
//    public ArrayList<String> getImages() {
//        return images;
//    }
//
//    public void setImages(ArrayList<String> images) {
//        this.images = images;
//    }
//
//    public String getName_product() {
//        return name_product;
//    }
//
//    public void setName_product(String name_product) {
//        this.name_product = name_product;
//    }
//
//    public String getDetail_product() {
//        return detail_product;
//    }
//
//    public void setDetail_product(String detail_product) {
//        this.detail_product = detail_product;
//    }
//
//    public IdDiscount getId_discount() {
//        return id_discount;
//    }
//
//    public void setId_discount(IdDiscount id_discount) {
//        this.id_discount = id_discount;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public int getQuantitySold() {
//        return quantitySold;
//    }
//
//    public void setQuantitySold(int quantitySold) {
//        this.quantitySold = quantitySold;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public Date getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(Date created_at) {
//        this.created_at = created_at;
//    }
//
//    public int get__v() {
//        return __v;
//    }
//
//    public void set__v(int __v) {
//        this.__v = __v;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeString(_id);
//        dest.writeStringList(images);
//        dest.writeString(name_product);
//        dest.writeString(detail_product);
//        dest.writeString(color);
//        dest.writeInt(quantity);
//        dest.writeInt(quantitySold);
//        dest.writeInt(price);
//        dest.writeInt(__v);
//    }
//}
public class ProductModel implements Parcelable {
    public String _id;
    public ArrayList<String> sizes; // Sử dụng ArrayList<String> thay cho ArrayList<Object>
    public ArrayList<String> images;
    public String name_product;
    public String detail_product;
    public IdDiscount id_discount;
    public String color;
    public int quantity;
    public int quantitySold;
    public int price;
    public Date created_at;
    public int __v;

    public ProductModel(String name_product, int price) {
        this.name_product = name_product;
        this.price = price;
    }

    protected ProductModel(Parcel in) {
        _id = in.readString();
        sizes = in.createStringArrayList(); // Đọc dữ liệu trực tiếp dưới dạng ArrayList<String>
        images = in.createStringArrayList();
        name_product = in.readString();
        detail_product = in.readString();
        color = in.readString();
        quantity = in.readInt();
        quantitySold = in.readInt();
        price = in.readInt();
        __v = in.readInt();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    // Các getter và setter khác

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeStringList(sizes); // Ghi dữ liệu trực tiếp dưới dạng ArrayList<String>
        dest.writeStringList(images);
        dest.writeString(name_product);
        dest.writeString(detail_product);
        dest.writeString(color);
        dest.writeInt(quantity);
        dest.writeInt(quantitySold);
        dest.writeInt(price);
        dest.writeInt(__v);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
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

    public IdDiscount getId_discount() {
        return id_discount;
    }

    public void setId_discount(IdDiscount id_discount) {
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
}

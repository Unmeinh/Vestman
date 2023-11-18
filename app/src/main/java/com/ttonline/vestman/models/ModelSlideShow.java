package com.ttonline.vestman.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class ModelSlideShow implements Parcelable {
    public String _id;
    public String thumbnailImage;
    public String description;
    public ProductModel id_product;
    public String expires_at;
    public String created_at;
    public int __v;


    protected ModelSlideShow(Parcel in) {
        _id = in.readString();
        thumbnailImage = in.readString();
        description = in.readString();
        id_product = in.readParcelable(ProductModel.class.getClassLoader());
        expires_at = in.readString();
        created_at = in.readString();
        __v = in.readInt();
    }

    public static final Creator<ModelSlideShow> CREATOR = new Creator<ModelSlideShow>() {
        @Override
        public ModelSlideShow createFromParcel(Parcel in) {
            return new ModelSlideShow(in);
        }

        @Override
        public ModelSlideShow[] newArray(int size) {
            return new ModelSlideShow[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductModel getProductModel() {
        return id_product;
    }

    public void setProductModel(ProductModel productModel) {
        this.id_product = productModel;
    }

    public ProductModel getId_product() {
        return id_product;
    }

    public void setId_product(ProductModel id_product) {
        this.id_product = id_product;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
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
        dest.writeString(thumbnailImage);
        dest.writeString(description);
        dest.writeParcelable(id_product, flags);
        dest.writeString(expires_at);
        dest.writeString(created_at);
        dest.writeInt(__v);
    }
}

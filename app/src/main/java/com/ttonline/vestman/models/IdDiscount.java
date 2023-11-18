package com.ttonline.vestman.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;


public class IdDiscount implements Parcelable {
    public String _id;
    public int value;
    public String started_at;
    public String expires_at;
    public int __v;


    protected IdDiscount(Parcel in) {
        _id = in.readString();
        value = in.readInt();
        started_at = in.readString();
        expires_at = in.readString();
        __v = in.readInt();
    }

    public static final Creator<IdDiscount> CREATOR = new Creator<IdDiscount>() {
        @Override
        public IdDiscount createFromParcel(Parcel in) {
            return new IdDiscount(in);
        }

        @Override
        public IdDiscount[] newArray(int size) {
            return new IdDiscount[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStarted_at() {
        return started_at;
    }

    public void setStarted_at(String started_at) {
        this.started_at = started_at;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
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
        dest.writeInt(value);
        dest.writeString(started_at);
        dest.writeString(expires_at);
        dest.writeInt(__v);
    }
}

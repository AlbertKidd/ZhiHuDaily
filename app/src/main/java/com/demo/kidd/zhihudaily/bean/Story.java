package com.demo.kidd.zhihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by niuwa on 2016/6/22.
 */
public class Story implements Parcelable {
    private int id;
    private String image;
    private String title;

    public Story(int id, String image, String title){
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.image);
        dest.writeString(this.title);
    }

    protected Story(Parcel in) {
        this.id = in.readInt();
        this.image = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}

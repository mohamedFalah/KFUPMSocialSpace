package com.example.android.kfupmsocialspace.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    private String title;
    private String image;
    private String page;

    News() {

    }

    News(String title, String image, String page) {
        this.title = title;
        this.image = image;
        this.page = page;
    }

    protected News(Parcel in) {
        title = in.readString();
        image = in.readString();
        page = in.readString();
    }



    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(page);
    }
}

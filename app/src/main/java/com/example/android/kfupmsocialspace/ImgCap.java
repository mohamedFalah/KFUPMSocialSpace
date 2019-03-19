package com.example.android.kfupmsocialspace;

import android.net.Uri;

public class ImgCap {

    private int position;
    private String caption;
    private Uri imagePath;

    public ImgCap(int position, String caption, Uri imagePath) {
        this.position = position;
        this.caption = caption;
        this.imagePath = imagePath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }
}
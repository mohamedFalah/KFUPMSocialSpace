package com.example.android.kfupmsocialspace.model;

public class News {


    private String title;
    private String image;


    News() {

    }

    News(String title, String image) {

        this.title = title;
        this.image = image;


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
}

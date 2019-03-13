package com.example.android.kfupmsocialspace.model;

public class News {

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
}

package com.example.android.kfupmsocialspace;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketItem {

    private String item_name;
    private int item_price;
    private String item_category;
    private String item_description;
    private int thumbnail;

    public MarketItem() {
    }

    public MarketItem(String item_name, int item_price, String item_category, String item_description, int thumbnail) {
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_category = item_category;
        this.item_description = item_description;
        this.thumbnail = thumbnail;
    }


    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

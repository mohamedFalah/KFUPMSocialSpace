package com.example.android.kfupmsocialspace.model;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketItem {

    private String itemName;
    private String itemPrice;
    private String itemCategory;
    private String itemDescription;
    private int thumbnail;

    public MarketItem() {
    }

    public MarketItem(String itemName, String itemPrice, String itemCategory, String itemDescription, int thumbnail) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.thumbnail = thumbnail;
    }

    public MarketItem(String itemName, String itemPrice, String itemCategory, String itemDescription) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
    }


    public String getItemName() { return itemName; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemPrice() { return itemPrice; }

    public void setItemPrice(String itemPrice) { this.itemPrice = itemPrice; }

    public String getItemCategory() { return itemCategory; }

    public void setItemCategory(String itemCategory) { this.itemCategory = itemCategory; }

    public String getItemDescription() { return itemDescription; }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }

    public int getThumbnail() { return thumbnail; }

    public void setThumbnail(int thumbnail) { this.thumbnail = thumbnail; }
}

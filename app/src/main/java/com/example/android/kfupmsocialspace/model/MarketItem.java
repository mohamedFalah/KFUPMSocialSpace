package com.example.android.kfupmsocialspace.model;

import android.os.Parcel;
import android.os.Parcelable;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketItem implements Parcelable {

    public static final Creator<MarketItem> CREATOR = new Creator<MarketItem>() {
        @Override
        public MarketItem createFromParcel(Parcel in) {
            return new MarketItem(in);
        }

        @Override
        public MarketItem[] newArray(int size) {
            return new MarketItem[size];
        }
    };
    private String itemName;
    private String itemPrice;
    private String itemCategory;
    private String itemDescription;
    private String itemPicture;
    private int thumbnail;
    //item owner information
    private String itemOwner;
    private String ownerID;

    public MarketItem() {
    }

    //constructor with thumbnail
    public MarketItem(String itemName, String itemPrice, String itemCategory, String itemDescription, int thumbnail) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.thumbnail = thumbnail;
    }

    //constructor with predefined picture
    public MarketItem(String itemName, String itemPrice, String itemCategory, String itemDescription) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
    }


    //constructor with item picture and item owner and id
    public MarketItem(String itemName, String itemPrice, String itemCategory, String itemDescription, String itemPicture,
                      String itemOwner, String ownerID) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemPicture = itemPicture;
        this.itemOwner = itemOwner;
        this.ownerID = ownerID;

    }

    protected MarketItem(Parcel in) {
        itemName = in.readString();
        itemPrice = in.readString();
        itemCategory = in.readString();
        itemDescription = in.readString();
        itemPicture = in.readString();
        thumbnail = in.readInt();
        itemOwner = in.readString();
        ownerID = in.readString();

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getItemOwner() {
        return itemOwner;
    }

    public void setItemOwner(String itemOwner) {
        this.itemOwner = itemOwner;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemPrice);
        dest.writeString(itemCategory);
        dest.writeString(itemDescription);
        dest.writeString(itemPicture);
        dest.writeInt(thumbnail);
        dest.writeString(itemOwner);
        dest.writeString(ownerID);
    }
}
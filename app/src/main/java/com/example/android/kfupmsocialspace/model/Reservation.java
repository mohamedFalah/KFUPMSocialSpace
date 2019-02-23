package com.example.android.kfupmsocialspace.model;

import java.sql.Time;

public class Reservation {

    private String productID;
    private String time;
    private String ownerID;


    public Reservation(){

    }

    public Reservation(String productID, String time, String ownerID){
        this.productID = productID;
        this.time = time;
        this.ownerID = ownerID;
    }


    public String getProductID() { return productID; }

    public void setProductID(String productID) { this.productID = productID; }


    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getOwnerID() { return ownerID; }

    public void setOwnerID(String userID) { this.ownerID = userID; }

}

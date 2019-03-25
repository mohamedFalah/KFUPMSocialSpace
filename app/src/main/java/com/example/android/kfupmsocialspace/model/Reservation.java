package com.example.android.kfupmsocialspace.model;

import java.sql.Time;

public class Reservation {

    private String productID;
    private String time;
    private String ownerID;
    private String reserverID;


    public Reservation(){

    }

    public Reservation(String productID, String time, String ownerID, String reserverID){
        this.productID = productID;
        this.time = time;
        this.ownerID = ownerID;
        this.reserverID = reserverID;
    }


    public String getProductID() { return productID; }

    public void setProductID(String productID) { this.productID = productID; }


    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }


    public String getOwnerID() { return ownerID; }

    public void setOwnerID(String userID) { this.ownerID = userID; }

    public String getReserverID() { return reserverID; }

    public void setReserverID(String reserverID) { this.reserverID = reserverID; }
}

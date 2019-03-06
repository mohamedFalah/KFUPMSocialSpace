package com.example.android.kfupmsocialspace.model;

public class Message {


    private String SenderID;
    private String SenderName;
    private String Message;
    private String Timestamp;
    private String Type;
    private String Image;
    private String voice;


    public Message(){

    }

    public Message(String senderID,String senderName,String message,String timestamp, String type){

        this.SenderID = senderID;
        this.SenderName = senderName;
        this.Message = message;
        this.Timestamp = timestamp;
        this.Type = type;

    }

    public Message(String senderID,String senderName,String message,String timestamp, String type, String image){

        this.SenderID = senderID;
        this.SenderName = senderName;
        this.Message = message;
        this.Timestamp = timestamp;
        this.Type = type;
        this.Image = image;

    }



    public String getSenderID() {
        return SenderID;
    }

    public String getSenderName() { return SenderName; }

    public String getMessage() { return Message; }

    public String getTimestamp() { return Timestamp; }

    public String getType() { return Type; }

    public String getImage() { return Image; }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setSenderName(String senderName) { SenderName = senderName; }

    public void setTimestamp(String timestamp) { Timestamp = timestamp; }

    public void setType(String type) { Type = type; }

    public void setImage(String image) { Image = image; }
}

package com.example.android.kfupmsocialspace.model;

public class Message {


    private String SenderID;
    private String SenderName;
    private String Message;


    public Message(){

    }

    public Message(String senderID, String message){

        this.SenderID = senderID;
        this.Message = message;

    }

    public String getSenderID() {
        return SenderID;
    }

    public String getSenderName() { return SenderName; }

    public String getMessage() {
        return Message;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setSenderName(String senderName) { SenderName = senderName; }
}

package com.example.android.kfupmsocialspace;

public class Message {


    private String SenderID;
    private String Message;


    Message(){

    }

    Message(String senderID, String message){

        this.SenderID = senderID;
        this.Message = message;

    }

    public String getSenderID() {
        return SenderID;
    }

    public String getMessage() {
        return Message;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public void setMessage(String message) {
        Message = message;
    }

}

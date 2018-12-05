package com.example.android.kfupmsocialspace;

public class Message {


    private String Text;
    private String senderID;

public Message (){

}

    public Message(String text, String senderID) {
        this.Text = text;
        this.senderID = senderID;
    }


    public String getText() {
        return Text;
    }

    public String getSenderID() {
        return senderID;
    }


    public void setText(String text) {
        this.Text = text;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}

package com.example.android.kfupmsocialspace.model;

public class ChatRoom {


    private String roomId;
    private String roomName;
    private String sectionNumber;


    public ChatRoom (String roomId, String roomName, String setionNumber){

        this.roomId = roomId;
        this.roomName = roomName;
        this.sectionNumber = setionNumber;

    }


    public String getRoomId() { return roomId; }

    public String getRoomName() { return roomName; }

    public String getSectionNumber() { return sectionNumber; }


    public void setRoomId(String roomId) { this.roomId = roomId; }

    public void setRoomName(String roomName) { this.roomName = roomName; }

    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }
}

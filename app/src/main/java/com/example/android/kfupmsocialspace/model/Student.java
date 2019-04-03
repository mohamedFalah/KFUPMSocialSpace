package com.example.android.kfupmsocialspace.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student extends User implements Parcelable {


    private String academicLevel;
    private String buildingNumber;
    private String major;
    private String roomNumber;
    private boolean roommateStatus;

    public Student(){

    }

    public Student(String email, String FirstName, String LastName, String phone,
                   String acadmicLevel, String buildingNumber, String major, String roomNumber, boolean roommateStatus){

        super(email, FirstName, LastName, phone);

        this.academicLevel = acadmicLevel;
        this.buildingNumber = buildingNumber;
        this.major = major;
        this.roommateStatus = roommateStatus;
        this.roomNumber = roomNumber;


    }


    //gets
    public String getAcademicLevel() {
        return academicLevel;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getMajor() {
        return major;
    }

    public String getRoomNumber() {
        return roomNumber;
    }


    //sets

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }


    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setRoommateStatus(boolean roommateStatus) {
        this.roommateStatus = roommateStatus;
    }


    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }




    //paracable

    protected Student(Parcel in) {
        academicLevel = in.readString();
        buildingNumber = in.readString();
        major = in.readString();
        roomNumber = in.readString();
        roommateStatus = in.readByte() != 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(academicLevel);
        dest.writeString(buildingNumber);
        dest.writeString(major);
        dest.writeString(roomNumber);
        dest.writeByte((byte) (roommateStatus ? 1 : 0));
    }
}

package com.example.android.kfupmsocialspace;

import android.os.Parcel;
import android.os.Parcelable;

public class RoommateRequest implements Parcelable {

    public static final Creator<RoommateRequest> CREATOR = new Creator<RoommateRequest>() {
        @Override
        public RoommateRequest createFromParcel(Parcel in) {
            return new RoommateRequest(in);
        }

        @Override
        public RoommateRequest[] newArray(int size) {
            return new RoommateRequest[size];
        }
    };
    private String requesterName;
    private String requesterCity;
    private String requesterMajor;
    private String requestDescription;

    public RoommateRequest() {
    }


    public RoommateRequest(String requesterName, String requesterCity, String requesterMajor, String requestDescription) {
        this.requesterName = requesterName;
        this.requesterCity = requesterCity;
        this.requesterMajor = requesterMajor;
        this.requestDescription = requestDescription;

    }

    protected RoommateRequest(Parcel in) {
        requesterName = in.readString();
        requesterCity = in.readString();
        requesterMajor = in.readString();
        requestDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requesterName);
        dest.writeString(requesterCity);
        dest.writeString(requesterMajor);
        dest.writeString(requestDescription);
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterCity() {
        return requesterCity;
    }

    public void setRequesterCity(String requesterCity) {
        this.requesterCity = requesterCity;
    }

    public String getRequesterMajor() {
        return requesterMajor;
    }

    public void setRequesterMajor(String requesterMajor) {
        this.requesterMajor = requesterMajor;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }
}

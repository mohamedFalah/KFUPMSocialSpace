package com.example.android.kfupmsocialspace.model;

import com.example.android.kfupmsocialspace.contract.UserContract;

public class User implements UserContract.IModel {

    private String email;
    private String firstName;
    private String lastName;
    private String token;

    //maybe converted to Number later
    private String phone;

    /// empty constructor for a reason not aware of now:))
    public User() {

    }

    public User(String email, String FirstName, String LastName, String phone) {

        this.email = email;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.phone = phone;

    }

    // user object without the user phone number
    public User(String email, String FirstName, String LastName) {

        this.email = email;
        this.firstName = FirstName;
        this.lastName = LastName;

    }


    //constructor with user token
    public User(String email, String FirstName, String LastName, String phone, String token) {

        this.email = email;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.phone = phone;
        this.token = token;

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    @Override
    public String getUserFullName() {
        return getFirstName() + " " + getLastName();
    }


}

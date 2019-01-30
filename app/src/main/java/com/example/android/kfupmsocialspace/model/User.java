package com.example.android.kfupmsocialspace.model;

import com.example.android.kfupmsocialspace.contract.UserContract;

public class User implements UserContract.IModel {

    private String email;
    private String firstName;
    private String lastName;

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


    @Override
    public String getUserFullName() {
        return getFirstName() + " " + getLastName();
    }
}

package com.example.android.kfupmsocialspace;

public class User {

    private String email;
    private String FirstName;
    private String LastName;

    //maybe converted to Number later
    private String phone;

    /// empty constructor for a reason not aware of now:))
    User() {

    }

    User(String email, String FirstName, String LastName, String phone) {

        this.email = email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.phone = phone;

    }

    // user object without the user phone number
    User(String email, String FirstName, String LastName) {

        this.email = email;
        this.FirstName = FirstName;
        this.LastName = LastName;

    }


    //setters
    public String getEmail() {
        return email;
    }

    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package com.example.android.kfupmsocialspace;

public class User {

        private String email;
        private String FirstName;
        private String LastName;
        private Number phone;

        /// empty constructor for a reason not aware of now:))
        User(){

        }

        User(String email, String FirstName, String LastName, Number phone){

            this.email      = email;
            this.FirstName  = FirstName;
            this.LastName   = LastName;
            this.phone      = phone;

        }


      //setters
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public Number getPhone() {
        return phone;
    }



    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setPhone(Number phone) {
        this.phone = phone;
    }
}

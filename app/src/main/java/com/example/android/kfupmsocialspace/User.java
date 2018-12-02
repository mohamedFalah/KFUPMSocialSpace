package com.example.android.kfupmsocialspace;

public class User {

        private String email;
        private String FirstName;
        private String LastName;

        //maybe converted to Number later
        private String phone;

        /// empty constructor for a reason not aware of now:))
        User(){

        }

        User(String email, String FirstName, String LastName, String phone){

            this.email      = email;
            this.FirstName  = FirstName;
            this.LastName   = LastName;
            this.phone      = phone;

        }

        // user object without the user phone number
        User(String email, String FirstName, String LastName){

        this.email      = email;
        this.FirstName  = FirstName;
        this.LastName   = LastName;

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

        public String getPhone() {
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

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

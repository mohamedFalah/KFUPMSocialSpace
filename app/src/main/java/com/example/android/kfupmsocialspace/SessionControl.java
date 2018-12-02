package com.example.android.kfupmsocialspace;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SessionControl extends Application {


    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if(firebaseUser != null){
            startActivity(

                    new Intent(SessionControl.this, SignIn.class)

            );
        }

    }





}

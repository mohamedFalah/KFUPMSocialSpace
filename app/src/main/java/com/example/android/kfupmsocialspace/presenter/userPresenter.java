package com.example.android.kfupmsocialspace.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.kfupmsocialspace.model.User;
import com.example.android.kfupmsocialspace.contract.UserContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;

public class userPresenter implements UserContract.IPresenter {


    User userModel;
    UserContract.IView view;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("User");

    //not used yet.
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public userPresenter(UserContract.IView newView, String UserID){

        //create user object with exitsting data from database
        if(UserID != null){
        dbRef.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled", databaseError.toException());
            }
        });}

        //create new view no idea why
        view = newView;


    }


    //return full name of the user
    @Override
    public void onSendClick() {

        String fullName = userModel.getUserFullName();

        view.onDataRecived(fullName);

    }
}







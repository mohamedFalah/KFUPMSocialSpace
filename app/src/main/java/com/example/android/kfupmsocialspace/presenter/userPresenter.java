package com.example.android.kfupmsocialspace.presenter;

import android.support.annotation.NonNull;

import com.example.android.kfupmsocialspace.model.User;
import com.example.android.kfupmsocialspace.model.UserContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userPresenter implements UserContract.IPresenter {


    User userModel;
    UserContract.IView view;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("User");


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();




    public userPresenter(UserContract.IView newView, String UserID){

        //private String currentUserID = mAuth.getCurrentUser().getUid();

        if(UserID != null){
        dbRef.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}


        view = newView;

    }

    @Override
    public void onSendClick() {

        String fullName = userModel.getUserFullName();

        view.onDataRecived(fullName);

    }
}







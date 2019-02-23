package com.example.android.kfupmsocialspace.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.MainActivity;
import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.contract.UserContract;
import com.example.android.kfupmsocialspace.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static android.support.constraint.Constraints.TAG;

public class userPresenter implements UserContract.IPresenter {


    User userModel;
    UserContract.IView view;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("User");
    private DatabaseReference dbRefTokens = database.getReference("UsersToken");

    //not used yet.
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    //to access it from a presenter
    userPresenter(String UserID) {
        getUserObject(UserID);
    }

    //empty constructor
    public userPresenter(){
        userToken();
    };


    public userPresenter(UserContract.IView newView, String UserID) {

        //create user object with exitsting data from database
        getUserObject(UserID);

        //create new view no idea why
        view = newView;


    }

    private void userToken(){


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }


                        // Get new Instance ID token
                        final String token= task.getResult().getToken();


                        //id of the current user
                        final String currentUser = mAuth.getCurrentUser().getUid();

                        dbRefTokens.child(currentUser).child("token").setValue(token);


                    }
                });


    }


    private void getUserObject(String UserID) {
        if (UserID != null) {
            dbRef.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userModel = dataSnapshot.getValue(User.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i(TAG, "onCancelled", databaseError.toException());
                }
            });
        }
    }






    //return full name of the user
    @Override
    public void onSendClick() {

        String fullName = userModel.getUserFullName();

        view.onDataRecived(fullName);

    }
}
package com.example.android.kfupmsocialspace.presenter;

import com.example.android.kfupmsocialspace.contract.RoommateContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoommatePresenter implements RoommateContract.IPresenter {



    //firebase connection
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Roommate");








}

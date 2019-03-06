package com.example.android.kfupmsocialspace.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.ChatActivity;
import com.example.android.kfupmsocialspace.contract.ChatContract;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatPresenter implements ChatContract.IPresenter {


    private Message message;
    private ChatContract.IView View;

    //database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    //user presenter
    private userPresenter userpresenter;


    public ChatPresenter(ChatContract.IView newView){

        userpresenter = new userPresenter();


        View = newView;

    }



    public void sendMsg(String messageText) {

        //to get the user name
        String userID = userpresenter.getUserID();
        String userName = userpresenter.userModel.getUserFullName();


        if (checkEmptyMessage(messageText)) {


            //message ID
            DatabaseReference push = dbRef.push();
            String messageID = push.getKey();

            //time of the message
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String time = format.format(calendar.getTime());
            //create message instance
            message = new Message(userID,userName,messageText,time,"text");


            //send the message
            dbRef.child("section2").child(messageID).setValue(message);

        }
    }




    private boolean checkEmptyMessage(String message){

        if(!message.isEmpty() && message.trim().length() > 0)
            return true;
        return false;
    }

}

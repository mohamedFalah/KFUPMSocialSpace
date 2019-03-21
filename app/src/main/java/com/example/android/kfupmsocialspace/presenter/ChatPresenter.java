package com.example.android.kfupmsocialspace.presenter;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.ChatActivity;
import com.example.android.kfupmsocialspace.contract.ChatContract;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatPresenter implements ChatContract.IPresenter {


    private Message message = new Message();
    private ChatContract.IView View;

    //database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("ChatImages");
    private StorageReference stRefDoc = FirebaseStorage.getInstance().getReference("ChatDocs");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    //user presenter
    private userPresenter userpresenter;


    public ChatPresenter(ChatContract.IView newView){

        userpresenter = new userPresenter();


        View = newView;

    }


    /*
            send text message
     */

    public void sendMsg(String messageText) {

        //to get the user name
        String userID = userpresenter.getUserID();
        String userName = userpresenter.userModel.getUserFullName();


        if (checkEmptyMessage(messageText)) {


            //message ID
            DatabaseReference push = dbRef.push();
            String messageID = push.getKey();


            //create message instance
            message =  new Message(userID,userName,messageText,getCurrentTime(),"text", "");


            //send the message
            dbRef.child("section2").child(messageID).setValue(message);

        }
    }


    /*
           send picture message
     */
    public void sendImageMessage(String pictureName, Uri uri){

        final StorageReference imageRef = stRef.child(pictureName);
        imageRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //get the picture uplaod reference from firebase storage


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful() && task.getException() != null) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Uri downloadUri = task.getResult();
                    String image = task.getResult().toString();
                    message = new Message(userpresenter.getUserID(), userpresenter.userModel.getUserFullName(),
                            "", getCurrentTime(),"image", image);


                    //upload the item to database
                    SendMediaMessage(message);

                } else {
                    // Handle failures
                    Log.d("uplaod log", "error while uploading");
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //show the failure message.
                    }
                });
    }

    /*
           send document message
     */
    public void sendDocumentMessage(String documentName, Uri documentUri) {
        final StorageReference documentRef = stRefDoc.child(documentName);
        documentRef.putFile(documentUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //get the picture uplaod reference from firebase storage


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful() && task.getException() != null) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return documentRef.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Uri downloadUri = task.getResult();
                    String document = task.getResult().toString();
                    message = new Message(userpresenter.getUserID(), userpresenter.userModel.getUserFullName(),
                            "", getCurrentTime(),"document", document);


                    //upload the item to database
                    SendMediaMessage(message);

                } else {
                    // Handle failures
                    Log.d("uplaod log", "error while uploading");
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //show the failure message.
                    }
                });

    }


    /*
            upload reference for storage to database
     */

    public void SendMediaMessage(Message message) {
        DatabaseReference push = dbRef.push();
        String messageID = push.getKey();
        if (messageID != null) {
            dbRef.child("section2").child(messageID).setValue(message);
        }

    }


    /*
            check the text message is not empty
     */
    private boolean checkEmptyMessage(String message){

        if(!message.isEmpty() && message.trim().length() > 0)
            return true;
        return false;
    }


    /*
            method to get the current time
     */
    private String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
        String time = format.format(calendar.getTime());

        return time;
    }

}

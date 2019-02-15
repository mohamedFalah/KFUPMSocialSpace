package com.example.android.kfupmsocialspace.presenter;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class MarketItemPresenter implements MarketitemContract.IPresenter {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Market Item");
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("MarketItemImages");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String userId;
    private String userName;

    MarketItem marketItem;
    MarketitemContract.IView view;
    double progress;


    //this wrong but why not
    private userPresenter userPresenter;

    public MarketItemPresenter(MarketitemContract.IView newView){
        getUser();
        userPresenter = new userPresenter(userId);
        view = newView;

    }

    @Override
    public void uploadItemImage(String pictureName, Uri uri, String itemName, String price, String category,

                                String itemDescription) {

        //item owner info
        final String Owner = userPresenter.userModel.getUserFullName();

        //item info
        final String itemN = itemName;
        final String itemP = price;
        final String itemC = category;
        final String itemDes = itemDescription;


        final StorageReference imageRef = stRef.child(pictureName);
         imageRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                view.progressBarValue(0);
                            }
                        }, 500);

                        //get the picture uplaod reference from firebase storage


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        view.progressBarValue((int) progress);

                    }
                })
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
                     public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String itemPicture = task.getResult().toString();
                            marketItem = new MarketItem(itemN, itemP, itemC, itemDes,
                                    itemPicture, Owner, userId);

                            //upload the item to database
                            uploadMarketItem(marketItem);
                        } else {
                            // Handle failures
                            // ...
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

    @Override
    public void uploadMarketItem(MarketItem marketItem) {
        String itemID = dbRef.push().getKey();
        if(itemID != null)
            dbRef.child(itemID).setValue(marketItem);

    }




    //get the user id
    private void getUser(){

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null)
            userId = user.getUid();

    }
}

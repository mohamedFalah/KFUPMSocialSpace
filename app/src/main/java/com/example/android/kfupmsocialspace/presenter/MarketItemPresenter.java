package com.example.android.kfupmsocialspace.presenter;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.model.Reservation;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.support.constraint.Constraints.TAG;


public class MarketItemPresenter implements MarketitemContract.IPresenter {


    private MarketItem marketItem;
    private MarketitemContract.IView view;
    private double progress;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Market Item");
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("MarketItemImages");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String userId;

    //public
    public boolean cannotReserve = false;

    //this wrong but why not
    private userPresenter userPresenter;

    public MarketItemPresenter(MarketitemContract.IView newView) {
        getUser();
        userPresenter = new userPresenter();
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
                        progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        view.progressBarValue((int) progress);

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
                    String itemPicture = task.getResult().toString();
                    marketItem = new MarketItem(itemN, itemP, itemC, itemDes,
                            itemPicture, Owner, userId);


                    //upload the item to database
                    uploadMarketItem(marketItem);

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

    @Override
    public void uploadMarketItem(MarketItem marketItem) {
        String itemID = dbRef.push().getKey();
        if (itemID != null) {
            marketItem.setItemID(itemID);
            marketItem.setStatus(false);
            dbRef.child(itemID).setValue(marketItem);
        }

    }

    @Override
    public void reserveItem(MarketItem marketItem) {
        //get the user id. as reservation id.
        getUser();

        String ownerID =  marketItem.getOwnerID();

        //get the current time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(calendar.getTime());

        //create reservation instance
        Reservation reservation = new Reservation(marketItem.getItemID(),time,marketItem.getOwnerID());

        if(userId != null) {
            if (!userId.equals(ownerID)) {
                dbRef.child(marketItem.getItemID()).child("status").setValue(true);

                database.getReference().child("ReservedItems").child(userId).setValue(reservation)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                view.reservationStatus(true);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                view.reservationStatus(false);
                            }
                        });
            }
            else{
                cannotReserve = true;
            }
        }

    }

    //get the user id
    private void getUser() {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null)
            userId = user.getUid();

    }
}
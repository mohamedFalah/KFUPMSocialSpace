package com.example.android.kfupmsocialspace.presenter;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class MarketItemPresenter implements MarketitemContract.IPresenter {


    private MarketItem marketItem;
    private MarketitemContract.IView view;
    private double progress;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference marketItemsRef = database.getReference("Market Item");
    private DatabaseReference reservationRef = database.getReference("ReservedItems");
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("MarketItemImages");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String userId;

    //Lists
    public List<MarketItem> MyItemsList = new ArrayList<>();
    public List<MarketItem> myReservedItemsList = new ArrayList<>();


    //public
    public boolean cannotReserve = false;

    //this wrong but why not
    private userPresenter userPresenter;

    public MarketItemPresenter(){

    }

    public MarketItemPresenter(MarketitemContract.IView newView) {
        getUser();
        userPresenter = new userPresenter();
        view = newView;

    }


    //uplaod the image item
    @Override
    public void uploadItemImage(String pictureName, Uri uri, String itemName, String price, String category,

                                String itemDescription, final String itemID) {

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
                            itemPicture, Owner, userId, getCurrentDate());

                    //for existing item to modify image
                    if(itemID != null)
                        marketItem.setItemID(itemID);


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


    //store the item in the datebase
    @Override
    public void uploadMarketItem(MarketItem marketItem) {

        if (marketItem.getItemID() != null) {
            marketItemsRef.child(marketItem.getItemID()).setValue(marketItem);

        } else {
            String itemID = marketItemsRef.push().getKey();
            marketItem.setStatus(false);
            if (itemID != null) {
                marketItem.setItemID(itemID);
                marketItem.setStatus(false);
                marketItemsRef.child(itemID).setValue(marketItem);
            }
        }
    }



    @Override
    public void reserveItem(MarketItem marketItem) {
        //get the user id. as reservation id.
        getUser();

        String ownerID =  marketItem.getOwnerID();

        //create reservation instance
        Reservation reservation = new Reservation(marketItem.getItemID(),getCurrenTime(),marketItem.getOwnerID(), userId);

        if(userId != null) {
            if (!userId.equals(ownerID)) {
                marketItemsRef.child(marketItem.getItemID()).child("status").setValue(true);
                String reservationID = reservationRef.push().getKey();
                reservation.setReservationID(reservationID);

                reservationRef.child(reservationID).setValue(reservation)
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


    //delete item
    public void deleteItem(MarketItem marketItem) {

        StorageReference photoReferance = FirebaseStorage.getInstance().getReferenceFromUrl(marketItem.getItemPicture());

        photoReferance.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("delete", "photo Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("delete", "photo not Deleted");
            }
        });

        marketItemsRef.child(marketItem.getItemID()).removeValue();



    }



    //return list of items that I reserved
    public void ReservedItems(){


        reservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    final Reservation reservation = snapshot.getValue(Reservation.class);

                    if(reservation.getReserverID().equals(userPresenter.getUserID())){
                        //get the items
                        marketItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                                    MarketItem marketItem = snapshot.getValue(MarketItem.class);
                                    if(reservation.getProductID().equals(marketItem.getItemID())) {

                                        myReservedItemsList.add(marketItem);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.i("items","kllkjlkjlkjljlkjlkjlkj reserved" +MyItemsList.size());
    }

    //return all my items that is not reserved
    public void MyItems(){

        MyItemsList.clear();
        marketItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MarketItem marketItem = snapshot.getValue(MarketItem.class);

                    if(marketItem.getOwnerID().equals(userPresenter.getUserID()) && marketItem.isStatus() == false){

                        MyItemsList.add(marketItem);
                        Log.i("items","kllkjlkjlkjljlkjlkjlkj myitems" +MyItemsList.size());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //return list of my items that is reserved by other
    public void MyItemsReserved(){

        MyItemsList.clear();
        marketItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MarketItem marketItem = snapshot.getValue(MarketItem.class);

                    if(marketItem.getOwnerID().equals(userPresenter.getUserID()) && marketItem.isStatus() == true){

                        MyItemsList.add(marketItem);
                        Log.i("items","kllkjlkjlkjljlkjlkjlkj myitems" +MyItemsList.size());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //handle the error
            }
        });


    }


    //get the reserver ID
    public void getReserverID(final MarketItem marketItem){

        reservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Reservation reservation = snapshot.getValue(Reservation.class);

                    if(reservation.getProductID().equals(marketItem.getItemID()) && reservation.getOwnerID().equals(marketItem.getOwnerID())){

                        userPresenter.getUserObject(reservation.getOwnerID());


                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //cancel reservation
    public void CancelReservation(final String ItemID){


        reservationRef.orderByChild("productID").startAt(ItemID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Reservation reservation = snapshot.getValue(Reservation.class);

                    if(reservation.getProductID().equals(ItemID))
                        reservationRef.child(reservation.getReservationID()).removeValue();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        marketItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    marketItem = snapshot.getValue(MarketItem.class);
                    if(marketItem.getItemID().equals(ItemID))
                        marketItem.setStatus(false);

                    uploadMarketItem(marketItem);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    ///get time in milliseconds!
    private String getCurrenTime(){
        Calendar calendar = Calendar.getInstance();
        long timeMilliseconds = calendar.getTimeInMillis();
        String time = String.valueOf(timeMilliseconds);

        return time;
    }

    //get the date of the today;
    private String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String month  = (String) DateFormat.format("MMM",  date); // Jun
        String year         = (String) DateFormat.format("yyyy", date); //2013

        String currentDate = dayOfTheWeek + " " + day + " " + month + " " + year;

        return currentDate;
    }

    //get the user id
    private void getUser() {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null)
            userId = user.getUid();

    }


}
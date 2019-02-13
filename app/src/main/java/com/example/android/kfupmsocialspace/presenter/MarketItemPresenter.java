package com.example.android.kfupmsocialspace.presenter;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class MarketItemPresenter implements MarketitemContract.IPresenter {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Market Item");
    private StorageReference stRef = FirebaseStorage.getInstance().getReference("MarketItemImages");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    MarketItem marketItem;
    MarketitemContract.IView view;
    double progress;

    public MarketItemPresenter(MarketitemContract.IView newView){

        view = newView;

    }

    @Override
    public void uploadItemImage(String name, Uri uri) {

        StorageReference imageRef = stRef.child(name);

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

                        //upload the item to database
                        marketItem = new MarketItem();

                        uploadMarketItem(marketItem);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        view.progressBarValue((int) progress);

                    }
                });
    }

    @Override
    public void uploadMarketItem(MarketItem marketItem) {

    }
}

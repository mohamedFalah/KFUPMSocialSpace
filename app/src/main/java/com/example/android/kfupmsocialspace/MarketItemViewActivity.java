package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.firebaseServices.FirebaseService;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketItemViewActivity extends AppCompatActivity implements View.OnClickListener, MarketitemContract.IView {

    private TextView itemName, itemPrice, itemCategory, itemDescription, itemOwner;
    private ImageView itemImg;
    private Button reserveItem;

    private MarketItem marketItem;

    //presenter
    private MarketItemPresenter marketItemPresenter;

    //Reservation
    private boolean reservationStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item_view);

        FirebaseService firebaseService = new FirebaseService();


        Bundle data = getIntent().getExtras();
        marketItem =  (MarketItem) data.getParcelable("clickedItem");

        marketItemPresenter = new MarketItemPresenter(this);

        itemName = findViewById(R.id.itemName);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemDescription = findViewById(R.id.itemDescription);
        itemImg = findViewById(R.id.item_thumbnail);
        itemCategory = findViewById(R.id.txtCat);
        itemOwner = findViewById(R.id.owner);
        reserveItem = findViewById(R.id.reserveItem);

        itemName.setText(marketItem.getItemName());
        itemDescription.setText(marketItem.getItemDescription());
        Picasso.with(this).load(Uri.parse(marketItem.getItemPicture())).fit().centerCrop().into(itemImg);
        itemOwner.setText(marketItem.getItemOwner());
        itemCategory.setText(marketItem.getItemCategory());
        itemPrice.setText(marketItem.getItemPrice());




        reserveItem.setOnClickListener(this);


    }

    @Override
    public void onStart(){
        super.onStart();


        if(marketItem.isStatus() == true)
            reserveItem.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    /*
            method to reserve an item
     */

    private void reserve() {

        marketItemPresenter.reserveItem(marketItem);

        if(marketItemPresenter.cannotReserve){
            Toast.makeText(this, "You cannot reserve your own item", Toast.LENGTH_SHORT).show();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (reservationStatus = true)
                        Toast.makeText(MarketItemViewActivity.this, "Item reserved", Toast.LENGTH_SHORT).show();
                }
            }, 800);
        }
    }

    @Override
    public void onClick(View v) {
        reserve();
    }


    @Override
    public void reservationStatus(boolean status) {

        reservationStatus = status;


    }



    /*
       not used but have to be implemented from interface
    */
    @Override
    public void progressBarValue(int progress) {

    }
}

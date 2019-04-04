package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.Adapter.MarketRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyReservedItemViewActivity extends AppCompatActivity implements MarketitemContract.IView {


    private ImageView itemImage;
    private TextView itemName, itemPrice, itemCategory, itemDescription;
    private Button relistItem, removeItem, viewReserverInfo;

    private MarketItem marketItem;

    private MarketItemPresenter marketItemPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reserved_item_view);



        marketItemPresenter = new MarketItemPresenter(this);

        //intilization
        itemImage = findViewById(R.id.item_thumbnail);
        itemName = findViewById(R.id.itemName);
        itemPrice= findViewById(R.id.item_price);
        itemCategory= findViewById(R.id.txtCat);
        itemDescription= findViewById(R.id.itemDescription);

        relistItem = findViewById(R.id.item_owner_remove_item_reservation_button);
        removeItem = findViewById(R.id.item_owner_remove_item_button);
        viewReserverInfo = findViewById(R.id.view_reserver_contact_information_button);


        ///set values
        Bundle data = getIntent().getExtras();
        marketItem = data.getParcelable("clickedItem");

        itemName.setText(marketItem.getItemName());
        itemDescription.setText(marketItem.getItemDescription());
        Picasso.with(this).load(Uri.parse(marketItem.getItemPicture())).fit().centerCrop().into(itemImage);
        itemCategory.setText(marketItem.getItemCategory());
        itemPrice.setText(marketItem.getItemPrice());


        //relist the item  after reservation cancel or not agreed
        relistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketItemPresenter.CancelReservation(marketItem.getItemID());


                Toast.makeText(getApplicationContext(), "item Re-listed", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MyReservedItemsActivity.class);
                        startActivity(intent);
                    }
                }, 400);

            }
        });



        //remove the item if the item sold
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                marketItemPresenter.deleteItem(marketItem);

                Toast.makeText(getApplicationContext(), "item Deleted", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MyReservedItemsActivity.class);
                        startActivity(intent);
                    }
                }, 400);


            }
        });

        //show the reserver info
        viewReserverInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ViewContactInformationActivity.class);
                intent.putExtra("owner", marketItem);
                intent.putExtra("showReseverinfo",true);
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();




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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }





    //not used
    @Override
    public void progressBarValue(int progress) {

    }

    @Override
    public void reservationStatus(boolean status) {

    }
}

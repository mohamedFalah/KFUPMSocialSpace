package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.squareup.picasso.Picasso;

public class BasketItemViewActivity extends AppCompatActivity implements MarketitemContract.IView {


    private ImageView itemImage;
    private TextView itemName, itemOwner, itemDate, itemPrice, itemCategory, itemDescription;
    private Button viewContactInfo, removeReservation;

    private MarketItem marketItem;

    private MarketItemPresenter marketItemPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_item_view);


        //presnter
        marketItemPresenter = new MarketItemPresenter(this);

        //intilization
        itemImage = findViewById(R.id.item_thumbnail);
        itemName = findViewById(R.id.itemName);
        itemOwner = findViewById(R.id.owner);
        itemDate= findViewById(R.id.date);
        itemPrice= findViewById(R.id.item_price);
        itemCategory= findViewById(R.id.txtCat);
        itemDescription= findViewById(R.id.itemDescription);

        viewContactInfo = findViewById(R.id.view_owner_contact_information_button);
        removeReservation = findViewById(R.id.reserver_remove_item_reservation);



        ///set values
        Bundle data = getIntent().getExtras();
        marketItem = data.getParcelable("clickedItem");

        itemName.setText(marketItem.getItemName());
        itemDescription.setText(marketItem.getItemDescription());
        Picasso.with(this).load(Uri.parse(marketItem.getItemPicture())).fit().centerCrop().into(itemImage);
        itemOwner.setText(marketItem.getItemOwner());
        itemCategory.setText(marketItem.getItemCategory());
        itemPrice.setText(marketItem.getItemPrice());

        if(marketItem.getItemTime() != null){
            itemDate.setText(marketItem.getItemTime());
        }
        else
            itemDate.setText("No date");



        //actions on buttons

        viewContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ViewContactInformationActivity.class);
                intent.putExtra("owner", marketItem);
                startActivity(intent);

            }
        });


        removeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                marketItemPresenter.CancelReservation(marketItem.getItemID());
                Toast.makeText(BasketItemViewActivity.this, "Reservation Cancelled", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), BasketItemsActivity.class);
                        startActivity(intent);
                    }
                }, 400);


            }
        });



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

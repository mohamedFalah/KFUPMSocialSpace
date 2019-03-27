package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.squareup.picasso.Picasso;

public class MyMarketItemViewActivity extends AppCompatActivity implements MarketitemContract.IView {

    private TextView itemName, itemPrice, itemCategory, itemDescription, itemOwner, itemDate;
    private ImageView itemImg;
    private Button editItem, removeItem;

    private MarketItem marketItem;


    MarketItemPresenter marketItemPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_market_item_view);



        Bundle data = getIntent().getExtras();
        marketItem =  (MarketItem) data.getParcelable("clickedItem");

        marketItemPresenter = new MarketItemPresenter(this);

        itemName = findViewById(R.id.itemName);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemDescription = findViewById(R.id.itemDescription);
        itemImg = findViewById(R.id.item_thumbnail);
        itemCategory = findViewById(R.id.txtCat);
        itemOwner = findViewById(R.id.owner);
        editItem = findViewById(R.id.edit_my_item_button);
        removeItem = findViewById(R.id.remove_my_item_button);


        itemName.setText(marketItem.getItemName());
        itemDescription.setText(marketItem.getItemDescription());
        Picasso.with(this).load(Uri.parse(marketItem.getItemPicture())).fit().centerCrop().into(itemImg);
        itemCategory.setText(marketItem.getItemCategory());
        itemPrice.setText(marketItem.getItemPrice());


        //edit button
        editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyMarketItemEditActivity.class);
                intent.putExtra("clickedItem", marketItem);
                startActivity(intent);
            }
        });

        //remove button
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                marketItemPresenter.deleteItem(marketItem);

            }
        });



    }


    //not used methods
    @Override
    public void progressBarValue(int progress) { }
    @Override
    public void reservationStatus(boolean status) { }
}

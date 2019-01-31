package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.model.MarketItem;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketItemViewActivity extends AppCompatActivity {

    private TextView itemName, itemPrice, itemCategory, itemDescription;
    private ImageView itemImg;

    private MarketItem marketItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item_view);

        itemName = findViewById(R.id.itemName);
//        itemPrice = (TextView) findViewById(R.id.item_price_id);
        itemDescription = findViewById(R.id.itemDescription);
        itemImg = findViewById(R.id.item_thumbnail);


        marketItem = (MarketItem) getIntent().getParcelableExtra("clickedItem");



        /*
        // Receive data
        Intent intent = getIntent();


        String Title = intent.getExtras().getString("Title");
//        String Price = intent.getExtras().getString("Price");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        // Setting values
        */
        itemName.setText(marketItem.getItemName());
//        itemPrice.setText(marketItem.getItemPrice());
        itemDescription.setText(marketItem.getItemDescription());
        itemImg.setImageResource(marketItem.getThumbnail());
    }
    
}

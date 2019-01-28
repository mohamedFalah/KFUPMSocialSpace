package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketItemViewActivity extends AppCompatActivity {

    private TextView itemName, itemPrice, itemCategory, itemDescription;
    private ImageView itemImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item_view);

        itemName = findViewById(R.id.item_name);
//        itemPrice = (TextView) findViewById(R.id.item_price_id);
        itemDescription = findViewById(R.id.item_description);
        itemImg = findViewById(R.id.item_thumbnail);

        // Receive data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
//        String Price = intent.getExtras().getString("Price");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        // Setting values

        itemName.setText(Title);
//        itemPrice.setText(Price);
        itemDescription.setText(Description);
        itemImg.setImageResource(image);
    }
}
package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.kfupmsocialspace.Adapter.MarketRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.model.Reservation;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlacedItemsActivity extends AppCompatActivity implements MarketitemContract.IView {


    List<MarketItem> MyItemsList = new ArrayList<>();
    private RecyclerView MyItems;
    MarketRecyclerViewAdapter myItemsAdapter;
    GridLayoutManager gridLayoutManager;

    MarketItemPresenter marketItemPresenter;
    userPresenter userPresenter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference marketItemsRef = database.getReference("Market Item");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_placed_items);


        marketItemPresenter = new MarketItemPresenter(this);
        userPresenter = new userPresenter();

        MyItems = findViewById(R.id.my_placed_items_recyclerview);
        myItemsAdapter = new MarketRecyclerViewAdapter(MyItemsList, this);
        gridLayoutManager = new GridLayoutManager(this,2);
        MyItems.setLayoutManager(gridLayoutManager);
        MyItems.setAdapter(myItemsAdapter);
        myItemsAdapter.notifyDataSetChanged();

    }


    @Override
    public void onStart(){
        super.onStart();


        marketItemPresenter.MyItems();
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 MyItemsList.addAll(marketItemPresenter.MyItemsList);
                 Log.i("items","kllkjlkjlkjljlkjlkjlkj myitems acti" +MyItemsList.size());
                 myItemsAdapter.notifyDataSetChanged();
             }
         },200);


        if(MyItemsList.size() == 0){
            //No items
        }

    }


    //not used funtions
    @Override
    public void progressBarValue(int progress) { }
    @Override
    public void reservationStatus(boolean status) { }
}

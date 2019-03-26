package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

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

public class ReservedItemsActivity extends AppCompatActivity implements MarketitemContract.IView {

    List<MarketItem> myReservedItemsList = new ArrayList<>();
    private RecyclerView MyReservedItems;
    MarketRecyclerViewAdapter myReservedItemsAdapter;
    GridLayoutManager gridLayoutManager;

    MarketItemPresenter marketItemPresenter;
    userPresenter userPresenter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reservationRef = database.getReference("ReservedItems");
    private DatabaseReference marketItemsRef = database.getReference("Market Item");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_items);

        marketItemPresenter = new MarketItemPresenter(this);
        userPresenter = new userPresenter();

        MyReservedItems = findViewById(R.id.reserved_item_recyclerview);
        myReservedItemsAdapter = new MarketRecyclerViewAdapter(myReservedItemsList, this);
        gridLayoutManager = new GridLayoutManager(this,2);
        MyReservedItems.setLayoutManager(gridLayoutManager);
        MyReservedItems.setAdapter(myReservedItemsAdapter);
        myReservedItemsAdapter.notifyDataSetChanged();

    }


    @Override
    public void onStart(){
        super.onStart();

        marketItemPresenter.myReservedItems();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myReservedItemsList.addAll(marketItemPresenter.myReservedItemsList);
                Log.i("items","kllkjlkjlkjljlkjlkjlkj myitems acti" +myReservedItemsList.size());
                myReservedItemsAdapter.notifyDataSetChanged();
            }
        },200);

        if(myReservedItemsList.size() == 0){
            //No items
        }

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



    //Not used methods
    @Override
    public void progressBarValue(int progress) { }
    @Override
    public void reservationStatus(boolean status) { }
}

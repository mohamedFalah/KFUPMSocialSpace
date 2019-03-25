package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.kfupmsocialspace.Adapter.MarketRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.model.Reservation;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlacedItemsActivity extends AppCompatActivity {


    List<MarketItem> MyItemsList = new ArrayList<>();
    private RecyclerView MyItems;
    MarketRecyclerViewAdapter myItemsAdapter;
    GridLayoutManager gridLayoutManager;

    userPresenter userPresenter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference marketItemsRef = database.getReference("Market Item");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_placed_items);


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

       // MyItemsList.clear();
        marketItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MarketItem marketItem = snapshot.getValue(MarketItem.class);

                    if(marketItem.getOwnerID().equals(userPresenter.getUserID())){

                     MyItemsList.add(marketItem);
                     myItemsAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

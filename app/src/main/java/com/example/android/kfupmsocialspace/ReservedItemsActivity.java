package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

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

public class ReservedItemsActivity extends AppCompatActivity {

    List<MarketItem> myReservedItemsList = new ArrayList<>();
    private RecyclerView MyReservedItems;
    MarketRecyclerViewAdapter myReservedItemsAdapter;
    GridLayoutManager gridLayoutManager;

    userPresenter userPresenter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reservationRef = database.getReference("ReservedItems");
    private DatabaseReference marketItemsRef = database.getReference("Market Item");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_items);

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

        reservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    final Reservation reservation = snapshot.getValue(Reservation.class);

                    if(reservation.getReserverID().equals(userPresenter.getUserID())){
                        //get the items
                        marketItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                                    MarketItem marketItem = snapshot.getValue(MarketItem.class);
                                    if(reservation.getProductID().equals(marketItem.getItemID())) {

                                        myReservedItemsList.add(marketItem);
                                        myReservedItemsAdapter.notifyDataSetChanged();

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
}

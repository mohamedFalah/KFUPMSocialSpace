package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.kfupmsocialspace.Adapter.MarketRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketFragment extends Fragment implements View.OnClickListener {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Market Item");

    List<MarketItem> marketItemList = new ArrayList<>();;
    RecyclerView market_recycler_view ;
    MarketRecyclerViewAdapter marketItemAdapter;
    GridLayoutManager gridLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.market_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_btn_add_market_item);
        fab.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddMarketItemActivity.class);
        startActivity(intent);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        /*
        marketItemList.add(new MarketItem("Selling PS4", 100, "Gaming", "New PS4 for sell", R.drawable.ps4));
        marketItemList.add(new MarketItem("Selling PS4", 140, "Gaming", "New PS4 for sell", R.drawable.ps4));
        marketItemList.add(new MarketItem("Selling PS4", 170, "Gaming", "New PS4 for sell", R.drawable.ps4));
        marketItemList.add(new MarketItem("Selling PS4", 90, "Gaming", "New PS4 for sell", R.drawable.ps4));
        marketItemList.add(new MarketItem("Selling PS4", 120, "Gaming", "New PS4 for sell"));
        */


        market_recycler_view = view.findViewById(R.id.recycler_market_items_list);
        marketItemAdapter = new MarketRecyclerViewAdapter(marketItemList, getContext());
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        market_recycler_view.setLayoutManager(gridLayoutManager);
        market_recycler_view.setAdapter(marketItemAdapter);

        //click event and pass data.
        marketItemAdapter.SetOnItemClickListener(new MarketRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                MarketItem marketItem = marketItemList.get(position);

                Intent intent = new Intent(getActivity(), MarketItemViewActivity.class);

                intent.putExtra("clickedItem", marketItem);

                startActivity(intent);

            }
        });

    }


    public void onStart() {

        super.onStart();

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                MarketItem marketItem = dataSnapshot.getValue(MarketItem.class);

                marketItemList.add(marketItem);
                gridLayoutManager.scrollToPosition(marketItemList.size() - 1);
                marketItemAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}

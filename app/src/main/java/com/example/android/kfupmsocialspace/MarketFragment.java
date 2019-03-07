package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.kfupmsocialspace.Adapter.MarketRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketFragment extends Fragment implements View.OnClickListener {


    List<MarketItem> marketItemList = new ArrayList<>();
    RecyclerView market_recycler_view;
    MarketRecyclerViewAdapter marketItemAdapter;

    GridLayoutManager gridLayoutManager;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Market Item");

    //https://stackoverflow.com/questions/29579811/changing-number-of-columns-with-gridlayoutmanager-and-recyclerview
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200; // You can vary the value held by the scalingFactor
        // variable. The smaller it is the more no. of columns you can display, and the
        // larger the value the less no. of columns will be calculated. It is the scaling
        // factor to tweak to your needs.
        int columnCount = (int) (dpWidth / scalingFactor);
        return (columnCount >= 2 ? columnCount : 2); // if column no. is less than 2, we still display 2 columns
    }

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
        gridLayoutManager = new GridLayoutManager(getContext(), calculateNoOfColumns(getContext()));
        market_recycler_view.setLayoutManager(gridLayoutManager);
        market_recycler_view.setAdapter(marketItemAdapter);
        marketItemAdapter.notifyDataSetChanged();

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

        marketItemList.clear();

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                MarketItem marketItem = dataSnapshot.getValue(MarketItem.class);
                marketItemList.add(marketItem);
                //no need to scroll here, this isn't the chat.
                //gridLayoutManager.scrollToPosition(marketItemList.size() - 1);
                marketItemAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
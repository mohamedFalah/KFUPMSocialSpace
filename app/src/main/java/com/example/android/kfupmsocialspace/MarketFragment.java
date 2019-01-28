package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketFragment extends Fragment implements View.OnClickListener {

    List<MarketItem> lstItems;

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
        lstItems = new ArrayList<>();
        lstItems.add(new MarketItem("Selling PS4", 100, "Gaming", "New PS4 for sell", R.drawable.ps4));
        lstItems.add(new MarketItem("Selling PS4", 140, "Gaming", "New PS4 for sell", R.drawable.ps4));
        lstItems.add(new MarketItem("Selling PS4", 170, "Gaming", "New PS4 for sell", R.drawable.ps4));
        lstItems.add(new MarketItem("Selling PS4", 90, "Gaming", "New PS4 for sell", R.drawable.ps4));
        lstItems.add(new MarketItem("Selling PS4", 120, "Gaming", "New PS4 for sell", R.drawable.ps4));

        RecyclerView market_recycler_view = view.findViewById(R.id.recycler_market_items_list);
        MarketRecyclerViewAdapter myAdapter = new MarketRecyclerViewAdapter(getContext(), lstItems);
        market_recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));
        market_recycler_view.setAdapter(myAdapter);
    }
}

package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.kfupmsocialspace.Adapter.RoommateRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoommateFragment extends Fragment implements View.OnClickListener {

    List<RoommateRequest> roommateRequestList = new ArrayList<>();
    RecyclerView roommate_recycler_view;
    RoommateRecyclerViewAdapter roommateAdapter;
    GridLayoutManager gridLayoutManager;

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
        View view = inflater.inflate(R.layout.roommate_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_btn_add_roommate_request);
        fab.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {


        roommateRequestList.add(new RoommateRequest("Ali", "Dammam", "SWE", "Null"));
        roommateRequestList.add(new RoommateRequest("Jawad", "Mekka", "CS", "Null"));
        roommateRequestList.add(new RoommateRequest("Mohammad", "Khobar", "COE", "Null"));


        roommate_recycler_view = view.findViewById(R.id.recycler_roommate_requests_list);
        roommateAdapter = new RoommateRecyclerViewAdapter(roommateRequestList);
        gridLayoutManager = new GridLayoutManager(getContext(), calculateNoOfColumns(getContext()));
        roommate_recycler_view.setLayoutManager(gridLayoutManager);
        roommate_recycler_view.setAdapter(roommateAdapter);


        //click event and pass data.
        roommateAdapter.SetOnItemClickListener(new RoommateRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RoommateRequest request = roommateRequestList.get(position);
                Intent intent = new Intent(getActivity(), RoommateRequestViewActivity.class);
                intent.putExtra("clickedItem", request);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddRoommateRequestActivity.class);
        startActivity(intent);
    }
}

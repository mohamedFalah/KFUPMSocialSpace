package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.kfupmsocialspace.Adapter.CategoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlogsFragment extends Fragment implements View.OnClickListener {


    List<String> categoryList = new ArrayList<>();
    RecyclerView categories;
    CategoryAdapter categoryAdapter;
    GridLayoutManager gridLayoutManager;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Blog");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blogs_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_btn_add_new_blog);
        fab.setOnClickListener(this);


        categories = view.findViewById(R.id.categories);
        categoryAdapter = new CategoryAdapter(categoryList, getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        categories.setLayoutManager(gridLayoutManager);
        categories.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        categoryAdapter.SetOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String categoryClicked = categoryList.get(position);
                Intent intent = new Intent(getActivity(), CategoryBlogsViewActivity.class);
                intent.putExtra("clickedCategory", categoryClicked);

                startActivity(intent);
            }
        });


        //https://stackoverflow.com/questions/34597334/android-change-menu-depending-on-selected-fragment/34597423#34597423
        setHasOptionsMenu(true);//Make sure you have this line of code.
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        categoryList.clear();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();
                    categoryList.add(category);
                    Log.i("happened", "this is done added " + category);
                    Log.i("happened", "this is done added");
                }

                categoryAdapter.notifyDataSetChanged();
                Log.i("happened", "this is done added" + categoryList.size());
                Log.i("happened", "this is done added " + dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //handle the error
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //https://stackoverflow.com/questions/34597334/android-change-menu-depending-on-selected-fragment/34597423#34597423
        menu.findItem(R.id.my_roommate_request).setVisible(false);
        menu.findItem(R.id.my_market_items).setVisible(false);
        menu.findItem(R.id.my_blogs).setVisible(true);
//        menu.findItem(R.id.blogs_search_top_bar_icon).setVisible(false);
        menu.findItem(R.id.roommate_search_top_bar_icon).setVisible(false);
        menu.findItem(R.id.market_search_top_bar_icon).setVisible(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddNewBlogActivity.class);
        startActivity(intent);
    }
}

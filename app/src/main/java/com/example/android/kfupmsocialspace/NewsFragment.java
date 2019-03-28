package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.kfupmsocialspace.Adapter.NewsAdapter;
import com.example.android.kfupmsocialspace.model.News;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    ArrayList<News> newsList = new ArrayList<>();
    ;
    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;
    GridLayoutManager gridLayoutManager;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("News");

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
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        gridLayoutManager = new GridLayoutManager(getContext(), calculateNoOfColumns(getContext()));

        newsRecyclerView = view.findViewById(R.id.newsRecycleView);
        newsAdapter = new NewsAdapter(newsList, getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        newsRecyclerView.setLayoutManager(gridLayoutManager);
        newsRecyclerView.setAdapter(newsAdapter);

        newsAdapter.SetOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                News news = newsList.get(position);

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getContext(), Uri.parse(news.getPage()));
            }
        });

        setHasOptionsMenu(true);//Make sure you have this line of code.
        return view;
    }

    public void onStart() {
        super.onStart();
        newsList.clear();

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                News news = dataSnapshot.getValue(News.class);
                newsList.add(news);
                //gridLayoutManager.scrollToPosition(newsList.size() - 1);
                newsAdapter.notifyDataSetChanged();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.my_roommate_request).setVisible(false);
        menu.findItem(R.id.my_market_items).setVisible(false);
        menu.findItem(R.id.my_blogs).setVisible(false);
        menu.findItem(R.id.blogs_search_top_bar_icon).setVisible(false);
        menu.findItem(R.id.roommate_search_top_bar_icon).setVisible(false);
        menu.findItem(R.id.market_search_top_bar_icon).setVisible(false);
    }
}

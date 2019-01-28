package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BlogsFragment extends Fragment implements View.OnClickListener {

    List<Blog> lstBlogs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blogs_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_btn_add_new_blog);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddNewBlogActivity.class);
        startActivity(intent);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        lstBlogs = new ArrayList<>();
        lstBlogs.add(new Blog("How to Study", "Education", "Hi everyone my name is ali and i would like to...", "Ali"));
        lstBlogs.add(new Blog("How to Play the game: abc", "Gaming", "Hi everyone my name is mohammad and i would like to...", "Mohammad"));
        lstBlogs.add(new Blog("How to Study", "Education", "Hi everyone my name is ali and i would like to...", "Ali"));
        lstBlogs.add(new Blog("How to Swim", "Education", "Hi ...", "Ali"));

        RecyclerView my_blogs_recycler_view = view.findViewById(R.id.recycler_blogs_list);
        BlogRecyclerViewAdapter myAdapter = new BlogRecyclerViewAdapter(getContext(), lstBlogs);
        my_blogs_recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 1));
        my_blogs_recycler_view.setAdapter(myAdapter);
    }
}

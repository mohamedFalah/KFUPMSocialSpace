package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.example.android.kfupmsocialspace.Adapter.CategoryAdapter;

public class BlogsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blogs_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_btn_add_new_blog);
        fab.setOnClickListener(this);


        GridView gridview = view.findViewById(R.id.grid_view_blog_categories);
        gridview.setAdapter(new CategoryAdapter(getContext()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CategoryBlogsViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddNewBlogActivity.class);
        startActivity(intent);
    }
}

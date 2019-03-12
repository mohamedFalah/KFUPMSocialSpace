package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.android.kfupmsocialspace.Adapter.BlogRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class CategoryBlogsViewActivity extends AppCompatActivity implements View.OnClickListener {

    List<Blog> lstBlogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_blogs_view);

        FloatingActionButton fab = findViewById(R.id.floating_btn_add_new_blog);
        fab.setOnClickListener(this);

        lstBlogs = new ArrayList<>();
        lstBlogs.add(new Blog("How to Study", "Education", "Hi everyone my name is ali and i would like to...", "Ali"));
        lstBlogs.add(new Blog("How to Play the game: abc", "Gaming", "Hi everyone my name is mohammad and i would like to...", "Mohammad"));
        lstBlogs.add(new Blog("How to Study", "Education", "Hi everyone my name is ali and i would like to...", "Ali"));
        lstBlogs.add(new Blog("How to Swim", "Education", "Hi ...", "Ali"));

        RecyclerView my_blogs_recycler_view = findViewById(R.id.recycler_blogs_list);
        BlogRecyclerViewAdapter myAdapter = new BlogRecyclerViewAdapter(this, lstBlogs);
        my_blogs_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        my_blogs_recycler_view.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AddNewBlogActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryBlogsViewActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Blog> listOfBlogs = new ArrayList<>();

    private String Category;
    private RecyclerView categoryBlogs;
    private BlogRecyclerViewAdapter blogsAdapter;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Blog");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_blogs_view);

        FloatingActionButton fab = findViewById(R.id.floating_btn_add_new_blog);
        fab.setOnClickListener(this);

        Intent intent = getIntent();
        Category = intent.getStringExtra("clickedCategory");

//        Log.i("category", "the firebase category is "+ Category);

        categoryBlogs = findViewById(R.id.recycler_blogs_list);
        blogsAdapter = new BlogRecyclerViewAdapter(this, listOfBlogs);
        categoryBlogs.setLayoutManager(new LinearLayoutManager(this));
        categoryBlogs.setAdapter(blogsAdapter);
        blogsAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();

        listOfBlogs.clear();
        dbRef.child(Category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Blog blog = ds.getValue(Blog.class);
                    listOfBlogs.add(blog);
                }


                blogsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //handle the error
            }
        });

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

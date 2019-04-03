package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.Adapter.BlogRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.contract.blogContract;
import com.example.android.kfupmsocialspace.model.Blog;
import com.example.android.kfupmsocialspace.presenter.BlogPresenter;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBlogsActivity extends AppCompatActivity implements blogContract.IView {

    List<Blog> myBlogsList = new ArrayList<>();
    private RecyclerView myBlogs;
    BlogRecyclerViewAdapter myBlogAdapter;
    GridLayoutManager gridLayoutManager;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Blog");

    //presenter
    BlogPresenter blogPresenter;
    userPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blogs);


        blogPresenter = new BlogPresenter(this);
        userPresenter = new userPresenter();

        myBlogs = findViewById(R.id.myBlogs);
        myBlogAdapter = new BlogRecyclerViewAdapter(this, myBlogsList);
        gridLayoutManager = new GridLayoutManager(this,1);
        myBlogs.setLayoutManager(gridLayoutManager);
        myBlogs.setAdapter(myBlogAdapter);
        myBlogAdapter.notifyDataSetChanged();

    }

    public void onStart(){
        super.onStart();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Blog blog = snapshot.getValue(Blog.class);
                    if (blog.getWriterID() != null) {
                        if (blog.getWriterID().equals(userPresenter.getUserID())) {
                            myBlogsList.add(blog);
                            myBlogAdapter.notifyDataSetChanged();
                        }
                    } else {
                        TextView myView = findViewById(R.id.no_blogs_written_textview);
                        myView.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // blogPresenter.getUserBlogs();

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

    @Override
    public void myblogs(List<Blog> myBlogsList) {
       this.myBlogsList = myBlogsList;
    }
}

package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private Blog blog;


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


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                showDialog("delete Blog");
                int itemPosition = viewHolder.getAdapterPosition();
                myBlogAdapter.notifyItemChanged(itemPosition);


            }
        }).attachToRecyclerView(myBlogs);




    }


    @Override
    protected void onStart(){
        super.onStart();

        myBlogsList.clear();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        blog = snapshot1.getValue(Blog.class);
                        if (blog.getWriterID().equals(userPresenter.getUserID())) {
                            myBlogsList.add(blog);
                            myBlogAdapter.notifyDataSetChanged();

                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(myBlogsList.size() == 0){
                    TextView myView = findViewById(R.id.no_blogs_written_textview);
                    myView.setVisibility(View.VISIBLE);
                }
            }
        }, 300);


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


    //show dialog

    public void showDialog(String title) {
        final AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        if (title != null) builder.setTitle(title);

        builder.setMessage("Do you want to delete Blog?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                blogPresenter.deleteBlog(blog,"removal");
                Toast.makeText(MyBlogsActivity.this, "Blog deleted", Toast.LENGTH_SHORT).show();
                onStart();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void myblogs(List<Blog> myBlogsList) {
       this.myBlogsList = myBlogsList;
    }
}

package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.Adapter.CommentAdapter;
import com.example.android.kfupmsocialspace.contract.blogContract;
import com.example.android.kfupmsocialspace.model.Blog;
import com.example.android.kfupmsocialspace.model.Comment;
import com.example.android.kfupmsocialspace.presenter.BlogPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class BlogViewActivity extends AppCompatActivity implements  blogContract.IView {

    private TextView blogTitle, blogSubject, blogWriterName, blogCategory, blogDate,
                     sendComment;

    private EditText comment;

    private  Blog blog;

    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView comments;
    private CommentAdapter commentAdapter;
    private GridLayoutManager gridLayoutManager;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Comment");

    //presenter
    BlogPresenter blogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_view);


        blogPresenter = new BlogPresenter(this);

        // Recieve data

        Bundle data = getIntent().getExtras();
        blog = data.getParcelable("clickedBlog");


        comments = findViewById(R.id.comments);
        commentAdapter = new CommentAdapter(commentList, this);
        gridLayoutManager = new GridLayoutManager(this, 1);
        comments.setLayoutManager(gridLayoutManager);
        comments.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();



        blogTitle = findViewById(R.id.blog_title_id);
        blogSubject = findViewById(R.id.blog_subject_id);
        blogWriterName = findViewById(R.id.blog_writer_name_id);
        blogCategory = findViewById(R.id.blog_category_id);
        blogDate = findViewById(R.id.blog_date_id);
        comment = findViewById(R.id.blog_message_string);

        sendComment =findViewById(R.id.blog_send_message);

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commentText = comment.getText().toString().trim();

                blogPresenter.sendComment(commentText, blog.getBlog_id());

                comment.setText(" ");
            }
        });




        blogTitle.setText(blog.getTitle());
        blogSubject.setText(blog.getSubject());
        blogWriterName.setText(blog.getWriter());
        blogCategory.setText(blog.getCategory());
        blogDate.setText(blog.getTime());


    }


    @Override
    protected void onStart() {
        super.onStart();


        commentList.clear();
        dbRef.child(blog.getBlog_id()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Comment comment = dataSnapshot.getValue(Comment.class);

                commentList.add(comment);
                gridLayoutManager.scrollToPosition(commentList.size() - 1);
                commentAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                comments.smoothScrollToPosition(comments.getAdapter().getItemCount());
                commentAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    //not used
    @Override
    public void myblogs(List<Blog> myBlogsList) {

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
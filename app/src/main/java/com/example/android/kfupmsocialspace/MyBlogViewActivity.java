package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.Adapter.CommentAdapter;
import com.example.android.kfupmsocialspace.contract.blogContract;
import com.example.android.kfupmsocialspace.model.Blog;
import com.example.android.kfupmsocialspace.model.Comment;
import com.example.android.kfupmsocialspace.presenter.BlogPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyBlogViewActivity extends AppCompatActivity implements blogContract.IView {


    private TextView blogTitle, blogSubject, blogCategory, blogDate;

    private Button remove, edit;

    private Blog blog;

    //presenter
    BlogPresenter blogPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog_view);



        blogPresenter = new BlogPresenter(this);

        // Recieve data

        Bundle data = getIntent().getExtras();
        blog = data.getParcelable("clickedBlog");






        blogTitle = findViewById(R.id.blog_title_id);
        blogSubject = findViewById(R.id.blog_subject_id);
        blogCategory = findViewById(R.id.blog_category_id);
        blogDate = findViewById(R.id.blog_date_id);
        edit = findViewById(R.id.edit_blog);
        remove = findViewById(R.id.remove_blog);

        blogTitle.setText(blog.getTitle());
        blogSubject.setText(blog.getSubject());
        blogCategory.setText(blog.getCategory());
        blogDate.setText(blog.getTime());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBlogEditActivity.class);
                intent.putExtra("clickedBlog", blog);
                startActivity(intent);
                finish();
            }
        });


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blogPresenter.deleteBlog(blog,"removal");
                Toast.makeText(getApplicationContext(), "Blog deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MyBlogsActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }




    //not used
    @Override
    public void myblogs(List<Blog> myBlogsList) {

    }
}

package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.blogContract;
import com.example.android.kfupmsocialspace.model.Blog;
import com.example.android.kfupmsocialspace.presenter.BlogPresenter;

import java.util.List;

public class AddNewBlogActivity extends AppCompatActivity implements View.OnClickListener, blogContract.IView{



    private EditText blogTitle, blogContent;
    private Spinner spinner;
    private Button addBlog;

    //presenter
    private BlogPresenter blogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_blog);

        blogPresenter = new BlogPresenter(this);
        blogTitle = findViewById(R.id.blogTitle);
        blogContent = findViewById(R.id.blogContent);
        spinner = findViewById(R.id.blog_category_spinner);
        addBlog = findViewById(R.id.add_blog);

        addBlog.setOnClickListener(this);




        //Add item to the category spinner
        //https://stackoverflow.com/questions/5241660/how-can-i-add-items-to-a-spinner-in-android
        String[] arraySpinner = new String[]{
                "-Category-", "game", "education", "entertainment", "Fun"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
    public void onClick(View v) {

        if(blogTitle != null && blogContent != null && spinner != null){


            String title = blogTitle.getText().toString().trim();
            String content = blogContent.getText().toString().trim();
            String category = spinner.getSelectedItem().toString();

            blogPresenter.uploadBlog(title,content,category);

            Toast.makeText(this, "blog added", Toast.LENGTH_SHORT).show();
            this.finish();


        }else{
            Toast.makeText(this, "Add values please", Toast.LENGTH_LONG).show();
        }





    }


    /*
            method not used here
     */
    @Override
    public void myblogs(List<Blog> myBlogsList) {

    }
}

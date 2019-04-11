package com.example.android.kfupmsocialspace;

import android.content.Intent;
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

public class MyBlogEditActivity extends AppCompatActivity implements blogContract.IView {



    private EditText blogTitle, blogContent;
    private Spinner spinner;
    private Button saveChange;

    private Blog blog;

    //presenter
    private BlogPresenter blogPresenter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog_edit);

        // Recieve data

        Bundle data = getIntent().getExtras();
        blog = data.getParcelable("clickedBlog");

        blogPresenter = new BlogPresenter(this);
        blogTitle = findViewById(R.id.blogTitle);
        blogContent = findViewById(R.id.blogContent);
        spinner = findViewById(R.id.blog_category_spinner);
        saveChange = findViewById(R.id.save_blog_changes);

        //Add item to the category spinner
        //https://stackoverflow.com/questions/5241660/how-can-i-add-items-to-a-spinner-in-android
        String[] arraySpinner = new String[]{
                "-Category-", "game", "education", "entertainment", "Fun"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        blogTitle.setText(blog.getTitle());
        blogContent.setText(blog.getSubject());
        int spinnerValue = adapter.getPosition(blog.getCategory());
        spinner.setSelection(spinnerValue);




        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = blogTitle.getText().toString().trim();
                String content = blogContent.getText().toString().trim();
                String category = spinner.getSelectedItem().toString();

                blogPresenter.deleteBlog(blog,"update");

                blog.setTitle(title);
                blog.setSubject(content);
                blog.setCategory(category);

                blogPresenter.updateBlog(blog);
                Toast.makeText(MyBlogEditActivity.this, "Blog updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MyBlogViewActivity.class);
                // passing data to the BlogViewActivity
                intent.putExtra("clickedBlog", blog);
                // start the activity
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

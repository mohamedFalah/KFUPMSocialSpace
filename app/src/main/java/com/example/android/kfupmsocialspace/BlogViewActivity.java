package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class BlogViewActivity extends AppCompatActivity {

    private TextView blogTitle, blogSubject, blogWriterName, blogCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_view);

        blogTitle = findViewById(R.id.card_view_blog_title_id);
//        blogSubject = findViewById(R.id.card_view_blog_subject_id);
        blogWriterName = findViewById(R.id.card_view_blog_writer_name_id);
        blogCategory = findViewById(R.id.card_view_blog_category_id);

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Subject = intent.getExtras().getString("Subject");
        String Writer = intent.getExtras().getString("Writer");
        String Category = intent.getExtras().getString("Category");

        // Setting values

//        blogTitle.setText(Title);
//        blogSubject.setText(Subject);
//        blogWriterName.setText(Writer);
//        blogCategory.setText(Category);

    }
}

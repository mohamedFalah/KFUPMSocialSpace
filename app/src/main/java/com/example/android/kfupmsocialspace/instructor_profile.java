package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class instructor_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button button = findViewById(R.id.edit_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instructor_profile.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    //https://stackoverflow.com/questions/14437745/how-to-override-action-bar-back-button-in-android
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
}

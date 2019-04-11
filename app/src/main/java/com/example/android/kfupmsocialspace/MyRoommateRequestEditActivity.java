package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MyRoommateRequestEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_roommate_request_edit);
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
}

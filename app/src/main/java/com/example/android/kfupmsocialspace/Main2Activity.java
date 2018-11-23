package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_chats:
                    //Original code
                    //mTextMessage.setText(R.string.title_home);

                    //Change this to Course chat layout later
                    setContentView(R.layout.activity_main2);
                    return true;

                case R.id.navigation_blogs:
                    mTextMessage.setText(R.string.title_blogs);
                    return true;

                case R.id.navigation_roommate:
                    mTextMessage.setText(R.string.title_roommate);
                    return true;

                case R.id.navigation_market:
                    //Original code
                    //mTextMessage.setText(R.string.title_market);
                    setContentView(R.layout.activity_market);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Change this to Course chat layout later
        setContentView(R.layout.activity_main2);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

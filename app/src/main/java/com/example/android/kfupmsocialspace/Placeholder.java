package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Placeholder extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Placeholder.class);
        startActivity(intent);
        finish();
        intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}

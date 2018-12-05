package com.example.android.kfupmsocialspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserId = mAuth.getCurrentUser().getUid();



    private ImageButton chatAttachFileBtn ;
    private ImageButton chatSendBtn;
    private EditText chatMsgField;
    private RecyclerView mMessagesList;


    public BottomNavigationView.OnNavigationItemSelectedListener chatBotNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Activity activity = null;
                    switch (item.getItemId()) {
                        case (R.id.chat):
                            activity = new ChatActivityChatFragment();
                            break;
                        case (R.id.chat_files):
                            selectedFragment = new ChatFilesFragment();
                            break;
                        case (R.id.group):
                            selectedFragment = new ChatGroupFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.chat_fragment_container,
                                selectedFragment).commit();
                    }
                    if (activity != null) {
                        Intent intent = new Intent(getApplicationContext(), ChatActivityChatFragment.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }


                    return true;
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);




        BottomNavigationViewEx chatBottomNav = findViewById(R.id.chat_bottom_navigation);
        chatBottomNav.setOnNavigationItemSelectedListener(chatBotNavListener);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.chat_fragment_container,
                    new ChatsFragment()).commit();
            chatBottomNav.setSelectedItemId(R.id.chat);
        }



    }



}

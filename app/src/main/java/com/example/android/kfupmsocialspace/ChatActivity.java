package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mMessagesList;

    public BottomNavigationView.OnNavigationItemSelectedListener chatBotNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case (R.id.chat):
                            selectedFragment = new ChatActivityChatFragment();
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
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessagesList = findViewById(R.id.messages_list);

        BottomNavigationViewEx chatBottomNav = findViewById(R.id.chat_bottom_navigation);
        chatBottomNav.setOnNavigationItemSelectedListener(chatBotNavListener);

        //the first thing you see when clicking on a chat is the chat and the messages
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.chat_fragment_container,
                    new ChatsFragment()).commit();
            chatBottomNav.setSelectedItemId(R.id.chat);
        }
    }
}

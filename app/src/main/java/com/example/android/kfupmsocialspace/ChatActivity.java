package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.chat_fragment_container,
                    new ChatsFragment()).commit();
            chatBottomNav.setSelectedItemId(R.id.chat);
        }
    }
}

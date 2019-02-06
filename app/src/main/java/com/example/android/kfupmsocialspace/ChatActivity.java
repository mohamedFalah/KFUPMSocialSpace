package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.Adapter.MessageAdapter;
import com.example.android.kfupmsocialspace.model.Message;
import com.example.android.kfupmsocialspace.contract.UserContract;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements UserContract.IView {

    ImageButton chatAttachFileBtn;
    ImageButton chatSendBtn;
    EditText chatMsgField;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserId = mAuth.getCurrentUser().getUid();
    private LinearLayoutManager linearLayoutManager;


    private final List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;

    //user presenter
    private userPresenter userpresenter;
    private String SenderName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        messageAdapter = new MessageAdapter(messageList);
        userMessagesList = findViewById(R.id.messages_list);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

        //initilaize user presenter
        userpresenter = new userPresenter(this, currentUserId);


        chatAttachFileBtn = findViewById(R.id.attach_file);
        chatSendBtn = findViewById(R.id.send_message);
        chatMsgField = findViewById(R.id.message_string);

        chatAttachFileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "File btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMsg();
            }
        });
    }

    //getting the messages
    protected void onStart() {

        super.onStart();

        dbRef.child("section2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);

                messageList.add(message);
                linearLayoutManager.scrollToPosition(messageList.size() - 1);
                messageAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


    // sending method
    private void sendMsg() {

        String msg = chatMsgField.getText().toString();

        //to get the user name
        userpresenter.onSendClick();

        Toast.makeText(ChatActivity.this, SenderName, Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(msg)) {

            DatabaseReference push = dbRef.push();
            String push_Id = push.getKey();


            //Arranging the structure of the data
            Map msgMap = new HashMap();
            msgMap.put("SenderID", currentUserId);
            msgMap.put("SenderName", SenderName);
            msgMap.put("Message", msg);

            Map map2 = new HashMap();
            map2.put("section2/" + push_Id, msgMap);

            dbRef.updateChildren(map2, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("CHAT_LOG", databaseError.getMessage());
                    }
                }
            });
            chatMsgField.setText("");
        }
    }

    //This part adds the three dots on the top right
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.chat_info:
                intent = new Intent(this, ChatInfoActivity.class);
                break;
            case R.id.chat_files:
                intent = new Intent(this, ChatFilesActivity.class);
                break;
            case R.id.chat_group:
                intent = new Intent(this, ChatGroupActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }


    @Override
    public void onDataRecived(String userFullName) {
        SenderName = userFullName;

    }
}

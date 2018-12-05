package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class ChatActivityChatFragment extends AppCompatActivity {

    ImageButton chatAttachFileBtn;
    ImageButton chatSendBtn;
    EditText chatMsgField;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserId = mAuth.getCurrentUser().getUid();

<<<<<<< HEAD


    private final List<Message> messageList = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;

    private MessageAdapter messageAdapter;

    private RecyclerView userMessagesList;


    ImageButton chatAttachFileBtn;
    ImageButton chatSendBtn ;
    EditText chatMsgField ;


    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_chat_fragment);


        chatAttachFileBtn =  findViewById(R.id.attach_file);
        chatSendBtn = findViewById(R.id.send_message);
        chatMsgField =  findViewById(R.id.message_string);


        messageAdapter = new MessageAdapter(messageList);
        userMessagesList = findViewById(R.id.messages_list);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);



        chatAttachFileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is", Toast.LENGTH_SHORT).show();
            }
        });

        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                sendMsg();


            }




        });

        chatMsgField.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Do it.", Toast.LENGTH_SHORT).show();
            }
        });



    }




/*
=======
>>>>>>> 4d4ac3abd3e4ddbbabbfe850ed15c00ba4c4db59
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflates sets or shows the fragment selected in the first param
        View view =  inflater.inflate(R.layout.activity_chat_chat_fragment, container, false);

        messageAdapter = new MessageAdapter(messageList);
        userMessagesList = view.findViewById(R.id.messages_list);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

        return view;

    }

    */
/*
    public void onViewCreated(View view, Bundle savedInstanceState) {

        chatAttachFileBtn = view.findViewById(R.id.attach_file);
        chatSendBtn = view.findViewById(R.id.send_message);
        chatMsgField = view.findViewById(R.id.message_string);





        getMessages();
        chatAttachFileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMsg();
<<<<<<< HEAD


=======
>>>>>>> 4d4ac3abd3e4ddbbabbfe850ed15c00ba4c4db59
            }
        });

        chatMsgField.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
*/

    @Override
    protected void onStart(){

        super.onStart();

        dbRef.child("section2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);

                messageList.add(message);

                messageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    // sending method
    private void sendMsg() {


        String msg = chatMsgField.getText().toString();


        if(!TextUtils.isEmpty(msg)){

            DatabaseReference push = dbRef.push();
            String push_Id = push.getKey();

            //Aranging the strucure of the data
            Map msgMap = new HashMap();
<<<<<<< HEAD
            msgMap.put("senderID", currentUserId);
            msgMap.put("Text" , msg);
=======
            msgMap.put("id", currentUserId);
            msgMap.put("msg", msg);
>>>>>>> 4d4ac3abd3e4ddbbabbfe850ed15c00ba4c4db59

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
}
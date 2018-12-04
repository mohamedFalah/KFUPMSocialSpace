package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChatActivityChatFragment extends Fragment {

    ImageButton chatAttachFileBtn;
    ImageButton chatSendBtn;
    EditText chatMsgField;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserId = mAuth.getCurrentUser().getUid();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflates sets or shows the fragment selected in the first param
        return inflater.inflate(R.layout.activity_chat_chat_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        chatAttachFileBtn = view.findViewById(R.id.attach_file);
        chatSendBtn = view.findViewById(R.id.send_message);
        chatMsgField = view.findViewById(R.id.message_string);

        chatAttachFileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "This is", Toast.LENGTH_SHORT).show();
            }
        });

        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                sendMsg();

            }
        });

        chatMsgField.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Do it.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMsg() {
        String msg = chatMsgField.getText().toString();

        if (!TextUtils.isEmpty(msg)) {
            DatabaseReference push = dbRef.push();
            String push_Id = push.getKey();

            Map msgMap = new HashMap();
            msgMap.put("id", currentUserId);
            msgMap.put("msg", msg);

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
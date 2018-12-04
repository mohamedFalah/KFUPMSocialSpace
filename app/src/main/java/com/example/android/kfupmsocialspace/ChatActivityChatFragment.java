package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChatActivityChatFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflates sets or shows the fragment selected in the first param
        return inflater.inflate(R.layout.activity_chat_chat_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        ImageButton chatAttachFileBtn = view.findViewById(R.id.attach_file);
        ImageButton chatSendBtn = view.findViewById(R.id.send_message);
        final EditText chatMsgField = view.findViewById(R.id.message_string);

        chatAttachFileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "This is", Toast.LENGTH_SHORT).show();
            }
        });

        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = chatMsgField.getText().toString();
                chatMsgField.setText("");
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        chatMsgField.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Do it.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

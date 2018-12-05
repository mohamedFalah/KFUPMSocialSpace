package com.example.android.kfupmsocialspace;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Message> userMessageList;

    private FirebaseAuth firebaseAuth;

    public MessageAdapter(List<Message> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView senderMessage, receiverMessage;



        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessage = itemView.findViewById(R.id.sender_message);
            receiverMessage = itemView.findViewById(R.id.receiver_message);


        }
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_single_layout,viewGroup,false);

        firebaseAuth = FirebaseAuth.getInstance();
        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int i) {

        String messageSenderID = " ";

                if(firebaseAuth.getCurrentUser() != null)
                    messageSenderID= firebaseAuth.getCurrentUser().getUid();
        Message message = userMessageList.get(i);

        String fromUserID = message.getSenderID();


        holder.receiverMessage.setVisibility(View.INVISIBLE);

        if(fromUserID != null && fromUserID.equals(messageSenderID)){
            holder.senderMessage.setBackgroundResource(R.drawable.sender_message);
            holder.senderMessage.setTextColor(Color.WHITE);
            holder.senderMessage.setText(message.getText());

        }
        else {
            holder.senderMessage.setVisibility(View.INVISIBLE);

            holder.receiverMessage.setVisibility(View.VISIBLE);


            holder.receiverMessage.setBackgroundResource(R.drawable.message_text_background);
            holder.receiverMessage.setTextColor(Color.WHITE);
            holder.receiverMessage.setText(message.getText());


        }





    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

























}

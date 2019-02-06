package com.example.android.kfupmsocialspace.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Message> userMessageList;
    private FirebaseAuth firebaseAuth;

    public MessageAdapter(List<Message> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView senderMessage, receiverName, receiverMessage;
        public LinearLayout receiverMessageHolder;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sender_message);

            receiverMessageHolder  = itemView.findViewById(R.id.receiver_message_holder);
            receiverName = itemView.findViewById(R.id.receiver_name);
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

        //This should hide the name of the user and the message
//        holder.receiverName.setVisibility(View.INVISIBLE);
//        holder.receiverMessage.setVisibility(View.INVISIBLE);
        holder.receiverMessageHolder.setVisibility(View.INVISIBLE);

        if(fromUserID != null && fromUserID.equals(messageSenderID)){
            holder.senderMessage.setVisibility(View.VISIBLE);
            holder.senderMessage.setText(message.getMessage());
            holder.receiverMessageHolder.setVisibility(View.INVISIBLE);
        }
        else {
            holder.senderMessage.setVisibility(View.INVISIBLE);

            holder.receiverMessageHolder.setVisibility(View.VISIBLE);
//            holder.receiverName.setVisibility(View.VISIBLE);
//            holder.receiverMessage.setVisibility(View.VISIBLE);
            holder.receiverName.setText(message.getSenderName());
            holder.receiverMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }
}

package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> userMessageList;
    private FirebaseAuth firebaseAuth;
    Context context;

    public MessageAdapter(List<Message> userMessageList, Context context) {
        this.userMessageList = userMessageList;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int i) {

        String messageSenderID = " ";

        if (firebaseAuth.getCurrentUser() != null)
            messageSenderID = firebaseAuth.getCurrentUser().getUid();

        Message message = userMessageList.get(i);

        String fromUserID = message.getSenderID();

        //This should hide the name of the user and the message
//        holder.receiverName.setVisibility(View.INVISIBLE);
//        holder.receiverMessage.setVisibility(View.INVISIBLE);
        holder.receiverMessageHolder.setVisibility(View.INVISIBLE);

        if (fromUserID != null && fromUserID.equals(messageSenderID)) {

            if (message.getType().equals("text")) {
                holder.senderMessageHolder.setVisibility(View.VISIBLE);
                holder.senderMessage.setText(message.getMessage());
                holder.senderMessageTime.setText(message.getTimestamp());
                holder.senderImage.setVisibility(View.INVISIBLE);
                holder.receiverMessageHolder.setVisibility(View.INVISIBLE);

            } else {
                holder.senderMessageHolder.setVisibility(View.VISIBLE);
                holder.senderMessage.setVisibility(View.INVISIBLE);
                holder.senderMessageTime.setText(message.getTimestamp());
                Picasso.with(context).load(Uri.parse(message.getImage())).fit().centerCrop().into(holder.senderImage);
                holder.receiverMessageHolder.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.senderMessageHolder.setVisibility(View.INVISIBLE);

            if (message.getType().equals("text")) {
                holder.receiverMessageHolder.setVisibility(View.VISIBLE);
//            holder.receiverName.setVisibility(View.VISIBLE);
//            holder.receiverMessage.setVisibility(View.VISIBLE);
                holder.receiverName.setText(message.getSenderName());
                holder.receiverMessage.setText(message.getMessage());
                holder.receiverMessageTime.setText(message.getTimestamp());
                holder.receiverImage.setVisibility(View.INVISIBLE);
            } else {
                holder.receiverMessageHolder.setVisibility(View.VISIBLE);
//            holder.receiverName.setVisibility(View.VISIBLE);
//            holder.receiverMessage.setVisibility(View.VISIBLE);
                holder.receiverName.setText(message.getSenderName());
                holder.receiverMessage.setVisibility(View.INVISIBLE);
                holder.receiverMessageTime.setText(message.getTimestamp());
                Picasso.with(context).load(Uri.parse(message.getImage())).fit().centerCrop().into(holder.receiverImage);
            }
        }
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_single_layout, viewGroup, false);

        firebaseAuth = FirebaseAuth.getInstance();
        return new MessageViewHolder(view);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView senderMessage,senderMessageTime, receiverName, receiverMessage, receiverMessageTime;
        public LinearLayout senderMessageHolder, receiverMessageHolder;
        public ImageView senderImage, receiverImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            //sender
            senderMessageHolder = itemView.findViewById(R.id.sender_message_holder);
            senderMessage = itemView.findViewById(R.id.sender_message);
            senderMessageTime = itemView.findViewById(R.id.sender_message_time);
            senderImage = itemView.findViewById(R.id.sender_image);

            //reciever
            receiverMessageHolder = itemView.findViewById(R.id.receiver_message_holder);
            receiverName = itemView.findViewById(R.id.receiver_name);
            receiverMessage = itemView.findViewById(R.id.receiver_message);
            receiverMessageTime = itemView.findViewById(R.id.receiver_message_time);
            receiverImage =itemView.findViewById(R.id.receiver_image);
        }
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

}

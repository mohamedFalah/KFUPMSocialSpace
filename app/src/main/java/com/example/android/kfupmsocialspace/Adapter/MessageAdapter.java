package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.ImageSize;
import com.example.android.kfupmsocialspace.ImageZoomActivity;
import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> userMessageList;
    private FirebaseAuth firebaseAuth;
    private Context context;

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

        if (fromUserID != null && fromUserID.equals(messageSenderID)) {
            holder.receiverMessageHolder.setVisibility(View.GONE);
            holder.receiverImage.setImageDrawable(null);
            holder.receiverDoc.setVisibility(View.GONE);

            switch (message.getType()) {
                case "text":
                    textMessage(holder, message, "sender");
                    break;

                case "image":
                    ImageMessage(holder, message, "sender");
                    break;

                case "document":
                    DocumentMessage(holder, message, "sender");
                    break;
            }
        } else {
            holder.senderMessageHolder.setVisibility(View.GONE);

            //https://stackoverflow.com/questions/27743339/strange-behaviour-of-images-in-recyclerview
            holder.senderImage.setImageDrawable(null);

            switch (message.getType()) {
                case "text":
                    textMessage(holder, message, "receiver");
                    break;

                case "image":
                    ImageMessage(holder, message, "receiver");
                    break;

                case "document":
                    DocumentMessage(holder, message, "receiver");
                    break;
            }

            holder.senderMessageHolder.setVisibility(View.GONE);
            holder.senderImage.setImageDrawable(null);
            holder.senderDoc.setVisibility(View.GONE);
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

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

    private void textMessage(MessageViewHolder holder, Message message, String who) {

        switch (who) {

            case "sender":

                holder.senderMessageHolder.setVisibility(View.VISIBLE);
                holder.senderMessage.setText(message.getMessage());
                holder.senderMessageTime.setText(message.getTimestamp());
                holder.senderImage.setVisibility(View.GONE);
                holder.senderDoc.setVisibility(View.GONE);
                holder.receiverMessageHolder.setVisibility(View.INVISIBLE);
                break;

            case "receiver":

                holder.receiverMessageHolder.setVisibility(View.VISIBLE);
                holder.receiverName.setText(message.getSenderName());
                holder.receiverImage.setVisibility(View.GONE);
                holder.receiverDoc.setVisibility(View.GONE);
                holder.receiverMessage.setText(message.getMessage());
                holder.receiverMessageTime.setText(message.getTimestamp());

                break;

        }


    }

    private void ImageMessage(MessageViewHolder holder, final Message message, String who) {
        //This part here adjusts the ImageView Sizes.
        //Can Change second parameter to ImageSize.getImageHeight()
        LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(ImageSize.getImageWidth(), ImageSize.getImageHeight());

        switch (who) {

            case "sender":
                holder.senderImage.setLayoutParams(vp);
//                holder.senderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                holder.senderMessageHolder.setVisibility(View.VISIBLE);
                holder.senderMessage.setVisibility(View.INVISIBLE);
                holder.senderMessageTime.setText(message.getTimestamp());
                holder.senderDoc.setVisibility(View.GONE);
                holder.senderImage.setVisibility(View.VISIBLE);
                Picasso.with(context).load(Uri.parse(message.getMedia())).into(holder.senderImage);
                holder.receiverMessageHolder.setVisibility(View.INVISIBLE);

                holder.senderImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImageZoomActivity.class);
                        Bitmap bmp = BitmapFactory.decodeResource(view.getResources(), R.drawable.no_image);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        intent.putExtra("picture", byteArray);
                        context.startActivity(intent);
                    }
                });

                break;

            case "receiver":
                holder.receiverImage.setLayoutParams(vp);
                holder.receiverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                holder.receiverMessageHolder.setVisibility(View.VISIBLE);
                holder.receiverName.setText(message.getSenderName());
                holder.receiverMessage.setVisibility(View.INVISIBLE);
                holder.receiverDoc.setVisibility(View.GONE);
                holder.receiverMessageTime.setText(message.getTimestamp());
                holder.receiverImage.setVisibility(View.VISIBLE);
                Picasso.with(context).load(Uri.parse(message.getMedia())).into(holder.receiverImage);

                holder.receiverImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImageZoomActivity.class);
                        Bitmap bmp = BitmapFactory.decodeResource(view.getResources(), R.drawable.loading);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        intent.putExtra("picture", byteArray);
                        context.startActivity(intent);
                    }
                });
                break;
        }

    }

    private void DocumentMessage(MessageViewHolder holder, Message message, String who) {

        final String docUrl = message.getMedia();

        switch (who) {
            case "sender":
                holder.senderMessageHolder.setVisibility(View.VISIBLE);
                holder.senderMessage.setVisibility(View.INVISIBLE);
                holder.senderMessageTime.setText(message.getTimestamp());
                holder.senderImage.setVisibility(View.GONE);
                holder.senderDoc.setVisibility(View.VISIBLE);
                holder.senderDoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(context, Uri.parse(docUrl));
                    }
                });
                holder.receiverMessageHolder.setVisibility(View.INVISIBLE);
                break;

            case "receiver":
                holder.receiverMessageHolder.setVisibility(View.VISIBLE);
                holder.receiverName.setText(message.getSenderName());
                holder.receiverImage.setVisibility(View.GONE);
                holder.receiverMessage.setVisibility(View.INVISIBLE);
                holder.receiverDoc.setVisibility(View.VISIBLE);
                holder.receiverDoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(context, Uri.parse(docUrl));
                    }
                });
                holder.receiverMessageTime.setText(message.getTimestamp());

                break;
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView senderMessage, senderMessageTime, receiverName, receiverMessage, receiverMessageTime;
        LinearLayout senderMessageHolder, receiverMessageHolder;
        ImageView senderImage, receiverImage;
        Button senderDoc, receiverDoc;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            //sender
            senderMessageHolder = itemView.findViewById(R.id.sender_message_holder);
            senderMessage = itemView.findViewById(R.id.sender_message);
            senderMessageTime = itemView.findViewById(R.id.sender_message_time);
            senderImage = itemView.findViewById(R.id.sender_image);
            senderDoc = itemView.findViewById(R.id.sender_Doc);


            //reciever
            receiverMessageHolder = itemView.findViewById(R.id.receiver_message_holder);
            receiverName = itemView.findViewById(R.id.receiver_student_name);
            receiverMessage = itemView.findViewById(R.id.receiver_student_message);
            receiverMessageTime = itemView.findViewById(R.id.receiver_student_message_time);
            receiverImage = itemView.findViewById(R.id.receiver_student_image);
            receiverDoc = itemView.findViewById(R.id.receiver_student_Doc);
        }
    }
}

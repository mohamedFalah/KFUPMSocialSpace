package com.example.android.kfupmsocialspace.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.ChatRoom;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomsViewHolder>{

    private List<ChatRoom> chatRoomsList;
    private Message message;
    private OnItemClickListener  listener;


    //here for the now
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");

    public ChatRoomsAdapter(List<ChatRoom> chatRoomsList) {
        this.chatRoomsList = chatRoomsList;
    }


    @NonNull
    @Override
    public ChatRoomsAdapter.ChatRoomsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_chat_label, viewGroup, false);

        return new ChatRoomsAdapter.ChatRoomsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomsViewHolder holder, int i) {

        ChatRoom chatRoom = chatRoomsList.get(i);
        String chatRoomName = chatRoom.getRoomName();

        holder.roomName.setText(chatRoomName);
        holder.lastMessage(chatRoomName);


    }


     class ChatRoomsViewHolder extends RecyclerView.ViewHolder {

         TextView roomName, senderName, lastMessage, lastMessageTime;

         ChatRoomsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            roomName = itemView.findViewById(R.id.chat_name);

            //for the last message
            senderName = itemView.findViewById(R.id.last_sender_name);
            lastMessage = itemView.findViewById(R.id.message_string);
            lastMessageTime = itemView.findViewById(R.id.last_message_time);


             //on the click event
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     if (listener != null) {

                         int position = getAdapterPosition();

                         if (position != RecyclerView.NO_POSITION) {

                             listener.onItemClick(position);

                         }
                     }
                 }
             });

        }




         //get the last message  this could somewhere else (chat presenter)
         void lastMessage(String chatRoomName){

             dbRef.child(chatRoomName).orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     for(DataSnapshot data : dataSnapshot.getChildren()){

                         message = data.getValue(Message.class);

                     }

                     senderName.setText(message.getSenderName());
                     lastMessage.setText(message.getMessage());
                     lastMessageTime.setText(message.getTimestamp());


                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });

         }



     }

    @Override
    public int getItemCount() {
        return chatRoomsList.size();
    }


    /// interface for handling the click event on recycle view
    public interface OnItemClickListener{

        void onItemClick(int position);

    }

    // a method will be explained later
    public void SetOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }



}

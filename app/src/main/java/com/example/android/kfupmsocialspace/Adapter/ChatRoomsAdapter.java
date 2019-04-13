package com.example.android.kfupmsocialspace.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.ChatRoom;
import com.example.android.kfupmsocialspace.model.Message;
import com.google.firebase.FirebaseError;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.util.List;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomsViewHolder> {

    private List<ChatRoom> chatRoomsList;
    private Message message;
    private OnItemClickListener listener;
    
    
    private String roomName;
    private String sectionNumber;
    private int size;


    //here for the now
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("ChatRooms");

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

        holder.room.setText(chatRoom.getRoomName());
        holder.lastMessage(chatRoom.getRoomName(), chatRoom.getSectionNumber());


    }

    @Override
    public int getItemCount() {
        return chatRoomsList.size();
    }

    // a method will be explained later
    public void SetOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }


    /// interface for handling the click event on recycle view
    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    class ChatRoomsViewHolder extends RecyclerView.ViewHolder {

        TextView room, senderName, lastMessage, lastMessageTime;

        ChatRoomsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            room = itemView.findViewById(R.id.chat_name);

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
        void lastMessage(String roomName , String sectionNumber) {


            dbRef.child("Rooms").child(roomName).child(sectionNumber).child("Messages").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {

                        //dbRef.child("Rooms").child("ARE 301").child("30").child("Messages").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    message = data.getValue(Message.class);

                                }

                                senderName.setText(message.getSenderName());
                                if (message.getType().equals("text"))
                                    lastMessage.setText(message.getMessage());
                                else
                                    lastMessage.setText("Media");
                                lastMessageTime.setText(message.getTimestamp());

                            }
                            else {
                                senderName.setText("");
                                lastMessage.setText("");
                                lastMessageTime.setText("");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }

                    });


        }


    }


}

package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.Adapter.ChatRoomsAdapter;
import com.example.android.kfupmsocialspace.Adapter.MessageAdapter;
import com.example.android.kfupmsocialspace.model.ChatRoom;
import com.example.android.kfupmsocialspace.model.Message;
import com.example.android.kfupmsocialspace.presenter.RoomChatRequestPresenter;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment implements View.OnClickListener {

   // private TextView senderName, lastMessage, lastMessageTime;

    private final List<ChatRoom> chatRooms = new ArrayList<>();
    private ChatRoomsAdapter chatRoomsAdapter;
    private RecyclerView chatRoomsList;
    GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chats_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_btn_add_course);
        fab.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddCourseActivity.class);
        startActivity(intent);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
       RelativeLayout ongoing = view.findViewById(R.id.enter_chat);

        ///add one Room
        ChatRoom section2 = new ChatRoom("1","section2","2");
        chatRooms.add(section2);

        chatRoomsList = view.findViewById(R.id.courses_chats_recyclerview);
        chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        chatRoomsList.setLayoutManager(gridLayoutManager);
        chatRoomsList.setHasFixedSize(true);
        chatRoomsList.setAdapter(chatRoomsAdapter);
        chatRoomsAdapter.notifyDataSetChanged();


        //click event
        chatRoomsAdapter.SetOnItemClickListener(new ChatRoomsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                //will be used later
                //ChatRoom chatRoom = chatRooms.get(position);

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Log.i("hbihbihbhuby uhbhb"," here");
                startActivity(intent);

            }
        });


        ongoing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}

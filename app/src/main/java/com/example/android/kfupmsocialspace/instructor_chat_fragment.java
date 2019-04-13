


package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.kfupmsocialspace.Adapter.ChatRoomsAdapter;
import com.example.android.kfupmsocialspace.model.ChatRoom;

import java.util.ArrayList;
import java.util.List;

//import android.support.design.widget.FloatingActionButton;

public class instructor_chat_fragment extends Fragment implements View.OnClickListener {

    // private TextView senderName, lastMessage, lastMessageTime;

    private final List<ChatRoom> chatRooms = new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    private ChatRoomsAdapter chatRoomsAdapter;
    private RecyclerView chatRoomsList;
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_instructor_chat_fragment, container, false);
        com.github.clans.fab.FloatingActionButton course_fab = view.findViewById(R.id.floating_btn_add_section);
        course_fab.setOnClickListener(this);

        setHasOptionsMenu(true);//Make sure you have this line of code.

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), instructorAddSection.class);
        startActivity(intent);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        ///add Room
        chatRooms.add(new ChatRoom("1", "ARE 301", "30"));
        chatRooms.add(new ChatRoom("2", "ARE 301", "33"));
        chatRooms.add(new ChatRoom("3", "ENGL 101", "01"));

        chatRoomsList = view.findViewById(R.id.courses_chats_recyclerview);
        chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        chatRoomsList.setLayoutManager(gridLayoutManager);
        chatRoomsList.setHasFixedSize(true);
        chatRoomsList.setAdapter(chatRoomsAdapter);
        chatRoomsAdapter.notifyDataSetChanged();


        //click event
        chatRoomsAdapter.SetOnItemClickListener(new ChatRoomsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                ChatRoom chatRoom = chatRooms.get(position);


                Intent intent = new Intent(getActivity(), ChatActivity.class);

                // Sending choosing room info to next Activity
                Bundle bundle = new Bundle();
                bundle.putString("roomName" , chatRoom.getRoomName());
                bundle.putString("sectionNumber" , chatRoom.getSectionNumber());
                intent.putExtra("bundle", bundle);

                startActivity(intent);

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.my_roommate_request).setVisible(false);
        menu.findItem(R.id.my_market_items).setVisible(false);
        menu.findItem(R.id.my_blogs).setVisible(false);
//        menu.findItem(R.id.blogs_search_top_bar_icon).setVisible(false);
        menu.findItem(R.id.roommate_search_top_bar_icon).setVisible(false);
        menu.findItem(R.id.market_search_top_bar_icon).setVisible(false);
    }
}

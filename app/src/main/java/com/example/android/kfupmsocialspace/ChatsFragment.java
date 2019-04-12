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

public class ChatsFragment extends Fragment implements View.OnClickListener {

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
        View view = inflater.inflate(R.layout.chats_fragment, container, false);
        com.github.clans.fab.FloatingActionButton course_fab = view.findViewById(R.id.floating_btn_add_course);
        com.github.clans.fab.FloatingActionButton chat_fab = view.findViewById(R.id.floating_btn_add_club);
        course_fab.setOnClickListener(this);
        chat_fab.setOnClickListener(this);

        setHasOptionsMenu(true);//Make sure you have this line of code.

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.floating_btn_add_course:
                intent = new Intent(getContext(), AddCourseActivity.class);
                break;
            case R.id.floating_btn_add_club:
                intent = new Intent(getContext(), AddClubActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        ///add one Room
        ChatRoom section2 = new ChatRoom("1", "Section" + "30", "30");
        chatRooms.add(section2);
        chatRooms.add(section2);

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
                //will be used later
                //ChatRoom chatRoom = chatRooms.get(position);

                Intent intent = new Intent(getActivity(), ChatActivity.class);
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

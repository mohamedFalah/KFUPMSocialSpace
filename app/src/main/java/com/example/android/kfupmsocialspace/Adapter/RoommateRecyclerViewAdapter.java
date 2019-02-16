package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.RoommateRequest;

import java.util.List;

public class RoommateRecyclerViewAdapter extends RecyclerView.Adapter<RoommateRecyclerViewAdapter.roommateRequestViewHolder> {

    private List<RoommateRequest> RoommateRequestList;
    private Context mContext;
    private RoommateRecyclerViewAdapter.OnItemClickListener listener;

    public RoommateRecyclerViewAdapter(List<RoommateRequest> RoommateRequestList) {
        this.RoommateRequestList = RoommateRequestList;
        ///  this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final RoommateRecyclerViewAdapter.roommateRequestViewHolder holder, int i) {

        RoommateRequest request = RoommateRequestList.get(i);

        holder.requesterName.setText(request.getRequesterName());
        holder.requesterCity.setText(request.getRequesterCity());
        holder.requesterMajor.setText(request.getRequesterMajor());
    }

    @NonNull
    @Override
    public RoommateRecyclerViewAdapter.roommateRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_roommate, viewGroup, false);

        return new RoommateRecyclerViewAdapter.roommateRequestViewHolder(view, listener);
    }

    @Override
    public int getItemCount() {
        return RoommateRequestList.size();
    }

    // a method will be explained later
    public void SetOnItemClickListener(RoommateRecyclerViewAdapter.OnItemClickListener listener) {

        this.listener = listener;

    }

    /// interface for handling the click event on recycle view
    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public static class roommateRequestViewHolder extends RecyclerView.ViewHolder {
        public TextView requesterName, requesterCity, requesterMajor;

        public roommateRequestViewHolder(View itemView, final RoommateRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);

            requesterName = itemView.findViewById(R.id.roommate_requester_name);
            requesterCity = itemView.findViewById(R.id.roommate_requester_city);
            requesterMajor = itemView.findViewById(R.id.roommate_requester_major);

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
    }
}

package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.commentViewHolder> {

    private List<Comment> commentList;
    private Context mContext;
    private OnItemClickListener listener;



    public CommentAdapter(List<Comment> commentList, Context c) {
        this.commentList = commentList;
        mContext = c;
    }


    public class commentViewHolder extends RecyclerView.ViewHolder{

        private TextView comment, userName, date;


        public commentViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);


            comment = itemView.findViewById(R.id.blog_message_subject_id);
            userName = itemView.findViewById(R.id.blog_message_writer_name_id);
            date = itemView.findViewById(R.id.blog_message_date_id);

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



    @NonNull
    @Override
    public CommentAdapter.commentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_blog_message, viewGroup, false);


        return new commentViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.commentViewHolder holder, int position) {

        Comment comment = commentList.get(position);
        holder.comment.setText(comment.getContent());
        holder.userName.setText(comment.getUserName());
        holder.date.setText(comment.getTime());



    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public interface OnItemClickListener {

        void onItemClick(int position);

    }


    public void SetOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }

}

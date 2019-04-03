package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.BlogViewActivity;
import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.Blog;

import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class BlogRecyclerViewAdapter extends RecyclerView.Adapter<BlogRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Blog> blogList;

    public BlogRecyclerViewAdapter(Context mContext, List<Blog> mData) {
        this.mContext = mContext;
        this.blogList = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_blog, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.blog_title.setText(blogList.get(i).getTitle());
        myViewHolder.blog_writer.setText(blogList.get(i).getWriter());
        myViewHolder.blog_category.setText(blogList.get(i).getCategory());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BlogViewActivity.class);
                Blog blog = blogList.get(i);
                // passing data to the BlogViewActivity
                intent.putExtra("clickedBlog", blog);
                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

//        TextView blog_id;
        TextView blog_title;
        TextView blog_writer;
        TextView blog_category;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

//            blog_id = itemView.findViewById(R.id.card_view_blog_id);
            blog_title = itemView.findViewById(R.id.card_view_blog_title_id);
            blog_writer = itemView.findViewById(R.id.card_view_blog_writer_name_id);
            blog_category = itemView.findViewById(R.id.card_view_blog_category_id);

            cardView = itemView.findViewById(R.id.card_view_blog);
        }
    }
}

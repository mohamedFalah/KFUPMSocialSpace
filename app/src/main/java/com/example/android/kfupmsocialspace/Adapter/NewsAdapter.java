package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.ImageSize;
import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {



    private List<News> newsList;
    private Context mContext;

    private OnItemClickListener listener;

    public NewsAdapter(List<News> newsList, Context mContext) {
        this.newsList = newsList;
        this.mContext = mContext;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView NewsTitle;
        public ImageView NewsImage;

        public LinearLayout receiverMessageHolder;

        public NewsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            NewsTitle = itemView.findViewById(R.id.news_title);
            NewsImage = itemView.findViewById(R.id.news_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_news,viewGroup,false);

        return new NewsAdapter.NewsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int i) {

        News news = newsList.get(i);
        RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImageSize.getNewsImageHeight());
        holder.NewsImage.setLayoutParams(vp);

        Picasso.with(mContext).load(news.getImage()).into(holder.NewsImage);
        holder.NewsTitle.setText(news.getTitle());


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void SetOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}

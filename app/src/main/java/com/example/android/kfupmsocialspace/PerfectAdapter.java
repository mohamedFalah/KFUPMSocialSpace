package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PerfectAdapter extends RecyclerView.Adapter<PerfectAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ImgCap> imgCapsList;
    private ImageView mainStream;
    private ImageWithCaptionListener mCallBack;

    public PerfectAdapter(Context context, ArrayList<ImgCap> imgCapsList, ImageView mainStream, ImageWithCaptionListener mCallBack) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.imgCapsList = imgCapsList;
        this.mainStream = mainStream;
        this.mCallBack = mCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ImgCap element = imgCapsList.get(holder.getAdapterPosition());
        Glide.with(context).load(element.getImagePath()).into(holder.image);
        Glide.with(context).load(imgCapsList.get(0).getImagePath()).into(mainStream);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context).load(element.getImagePath()).into(mainStream);
                mCallBack.imgCaptionCallBack(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgCapsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
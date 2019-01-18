package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<MarketItem> mData;

    public RecyclerViewAdapter(Context mContext, List<MarketItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_market_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.item_name.setText(mData.get(i).getItem_name());
        myViewHolder.item_price.setText(String.valueOf(mData.get(i).getItem_price()));
        myViewHolder.item_thumbnail.setImageResource(mData.get(i).getThumbnail());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MarketItemViewActivity.class);

                // passing data to the MarketItemViewActivity
                intent.putExtra("Title", mData.get(i).getItem_name());
                intent.putExtra("Price", mData.get(i).getItem_price());
                intent.putExtra("Description", mData.get(i).getItem_description());
                intent.putExtra("Thumbnail", mData.get(i).getThumbnail());
                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;
        TextView item_price;
        ImageView item_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name_id);
            item_price = itemView.findViewById(R.id.item_price_id);
            item_thumbnail = itemView.findViewById(R.id.item_image_id);
            cardView = itemView.findViewById(R.id.cardview_market_item);
        }
    }
}

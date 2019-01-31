package com.example.android.kfupmsocialspace;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.model.MarketItem;

import java.util.List;

//https://www.youtube.com/watch?v=SD2t75T5RdY
public class MarketRecyclerViewAdapter extends RecyclerView.Adapter<MarketRecyclerViewAdapter.marketItemViewHolder> {

    private List<MarketItem> marketItemList;

    private OnItemClickListener listener;

    public MarketRecyclerViewAdapter(List<MarketItem> marketItemList) {
        this.marketItemList = marketItemList;
    }


    public static class marketItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemPrice;
        public ImageView itemThumbnail;

        CardView cardView;

        public marketItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name_id);
            itemPrice = itemView.findViewById(R.id.item_price_id);
            itemThumbnail = itemView.findViewById(R.id.item_image_id);
            cardView = itemView.findViewById(R.id.card_view_market_item);


            //on the click event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null) {

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
    public marketItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        view = mInflater.inflate(R.layout.cardview_market_item, viewGroup, false);
        return new marketItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull marketItemViewHolder holder, final int i) {

        MarketItem marketItem = marketItemList.get(i);

        holder.itemName.setText(marketItem.getItemName());
        holder.itemPrice.setText(String.valueOf(marketItem.getItemPrice()));
        holder.itemThumbnail.setImageResource(R.drawable.ps4);

    }

    @Override
    public int getItemCount() {
        return marketItemList.size();
    }



    /// interface for handling the click event on recycle view
    public interface OnItemClickListener{

        void onItemClick(int position);

    }

    // a method will be explained later
    public void SetOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }


}

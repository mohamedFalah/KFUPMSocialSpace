package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//this whole java file
//https://www.youtube.com/watch?v=SD2t75T5RdY

//for the dots on the carview of the market item
//https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
public class MarketRecyclerViewAdapter extends RecyclerView.Adapter<MarketRecyclerViewAdapter.marketItemViewHolder> implements Filterable {

    private List<MarketItem> marketItemList;
    private List<MarketItem> marketItemListFull;
    private Context mContext;
    private OnItemClickListener listener;
    private Filter marketFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MarketItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(marketItemListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (MarketItem item : marketItemListFull) {
                    if (item.getItemName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            marketItemList.clear();
            marketItemList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public MarketRecyclerViewAdapter(List<MarketItem> marketItemList, Context mContext) {
        this.marketItemList = marketItemList;
        marketItemListFull = new ArrayList<>(marketItemList);
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final marketItemViewHolder holder, int i) {

        MarketItem marketItem = marketItemList.get(i);

        holder.itemName.setText(marketItem.getItemName());
        holder.itemPrice.setText(String.valueOf(marketItem.getItemPrice()));


        if (marketItem.getItemPicture() != null) {

            holder.myloadingPanel.setVisibility(View.VISIBLE);
            holder.myItemNameAndPriceHolder.setVisibility(View.INVISIBLE);
            Uri imageUri = Uri.parse(marketItem.getItemPicture());

//            Picasso.with(mContext).load(imageUri).fit().centerCrop().into(holder.itemThumbnail);
//            Picasso.with(mContext).load(imageUri).error(R.drawable.no_image).placeholder(R.drawable.loading_image).into(holder.itemThumbnail);

            //https://stackoverflow.com/questions/26548660/how-to-listen-for-picasso-android-load-complete-events
            Picasso.with(mContext)
                    .load(imageUri)
                    .into(holder.itemThumbnail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.myItemNameAndPriceHolder.setVisibility(View.VISIBLE);
                            holder.myloadingPanel.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            //Fill this one day.
                        }
                    });
        } else {
            holder.myloadingPanel.setVisibility(View.GONE);
            holder.itemThumbnail.setImageResource(R.drawable.no_image);
        }
    }

    @NonNull
    @Override
    public marketItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_market_item, viewGroup, false);

        return new marketItemViewHolder(view, listener);
    }

    @Override
    public int getItemCount() {
        return marketItemList.size();
    }

    // a method will be explained later
    public void SetOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }

    @Override
    public Filter getFilter() {
        return marketFilter;
    }

    /// interface for handling the click event on recycle view
    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public static class marketItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice;
        private ImageView itemThumbnail;

        private RelativeLayout myloadingPanel, myItemNameAndPriceHolder;

        private marketItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            myloadingPanel = itemView.findViewById(R.id.loadingPanel);
            myItemNameAndPriceHolder = itemView.findViewById(R.id.item_name_and_price);

            itemName = itemView.findViewById(R.id.item_name_id);
            itemPrice = itemView.findViewById(R.id.item_price_id);
            itemThumbnail = itemView.findViewById(R.id.item_image_id);

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
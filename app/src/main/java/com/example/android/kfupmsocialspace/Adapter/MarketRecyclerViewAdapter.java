package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.R;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.squareup.picasso.Picasso;

import java.util.List;

//this whole java file
//https://www.youtube.com/watch?v=SD2t75T5RdY

//for the dots on the carview of the market item
//https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
public class MarketRecyclerViewAdapter extends RecyclerView.Adapter<MarketRecyclerViewAdapter.marketItemViewHolder> {

    private List<MarketItem> marketItemList;
    private Context mContext;
    private OnItemClickListener listener;

    public MarketRecyclerViewAdapter(List<MarketItem> marketItemList, Context mContext) {
        this.marketItemList = marketItemList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final marketItemViewHolder holder, int i) {

        MarketItem marketItem = marketItemList.get(i);

        holder.itemName.setText(marketItem.getItemName());
        holder.itemPrice.setText(String.valueOf(marketItem.getItemPrice()));

        if(marketItem.getItemPicture() != null){
            Uri imageUri = Uri.parse(marketItem.getItemPicture());
            Picasso.with(mContext).load(imageUri).fit().centerCrop().into(holder.itemThumbnail);
        }else{
            holder.itemThumbnail.setImageResource(R.drawable.ps4);
        }

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showPopupMenu(holder.overflow);
            }
        });
    }

    @NonNull
    @Override
    public marketItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_market_item,viewGroup,false);

        return new marketItemViewHolder(view, listener);
    }

    /**
     * Showing popup menu when tapping on 3 dots

     private void showPopupMenu(View view) {
     // inflate menu
     PopupMenu popup = new PopupMenu(mContext, view);
     MenuInflater inflater = popup.getMenuInflater();
     inflater.inflate(R.menu.menu_market_cardview, popup.getMenu());
     popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
     popup.show();
     }
     */

    public static class marketItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemPrice;
        public ImageView itemThumbnail, overflow;

        public marketItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name_id);
            itemPrice = itemView.findViewById(R.id.item_price_id);
            itemThumbnail = itemView.findViewById(R.id.item_image_id);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);

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

    @Override
    public int getItemCount() {
        return marketItemList.size();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
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
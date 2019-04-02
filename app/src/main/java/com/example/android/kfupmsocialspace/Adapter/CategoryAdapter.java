package com.example.android.kfupmsocialspace.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder> {

    private List<String> categoryList;
    private Context mContext;
    private OnItemClickListener listener;



    public CategoryAdapter(List<String> categoryList, Context c) {
        this.categoryList = categoryList;
        mContext = c;
    }


    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_category, viewGroup, false);


        return new categoryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.categoryViewHolder holder, int i) {

//        holder.categoryNameHolder.setVisibility(View.VISIBLE);
        holder.categoryName.setText(categoryList.get(i));


    }

   public class categoryViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryName;
        private LinearLayout categoryNameHolder;


       public categoryViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
           super(itemView);


            categoryName = itemView.findViewById(R.id.categoryName);
//            categoryNameHolder = itemView.findViewById(R.id.categoryNameHolder);


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
        return categoryList.size();
    }


    public interface OnItemClickListener {

        void onItemClick(int position);

    }


    public void SetOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }
}
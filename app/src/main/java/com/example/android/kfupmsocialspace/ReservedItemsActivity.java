package com.example.android.kfupmsocialspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.Adapter.MarketRecyclerViewAdapter;
import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.example.android.kfupmsocialspace.presenter.userPresenter;

import java.util.ArrayList;
import java.util.List;

public class ReservedItemsActivity extends AppCompatActivity implements MarketitemContract.IView {


    List<MarketItem> ReservedItemsList = new ArrayList<>();
    MarketRecyclerViewAdapter ReservedItemsAdapter;
    GridLayoutManager gridLayoutManager;
    MarketItemPresenter marketItemPresenter;
    userPresenter userPresenter;
    private RecyclerView ReservedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_items);


        marketItemPresenter = new MarketItemPresenter(this);
        userPresenter = new userPresenter();

        ReservedItems = findViewById(R.id.reserved_items_recyclerview);
        ReservedItemsAdapter = new MarketRecyclerViewAdapter(ReservedItemsList, this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        ReservedItems.setLayoutManager(gridLayoutManager);
        ReservedItems.setAdapter(ReservedItemsAdapter);
        ReservedItemsAdapter.notifyDataSetChanged();


        ReservedItemsAdapter.SetOnItemClickListener(new MarketRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                MarketItem marketItem = ReservedItemsList.get(position);
                Intent intent = new Intent(getApplicationContext(), ReservedItemViewActivity.class);
                intent.putExtra("clickedItem", marketItem);
                startActivity(intent);

            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();



        marketItemPresenter.ReservedItems();

        ReservedItemsList.clear();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ReservedItemsList.addAll(marketItemPresenter.myReservedItemsList);
                Log.i("items", "kllkjlkjlkjljlkjlkjlkj myitems acti" + ReservedItemsList.size());
                ReservedItemsAdapter.notifyDataSetChanged();

                if (ReservedItemsList.size() == 0) {
                    //No items
                    TextView myView = findViewById(R.id.no_reserved_items_textview);
                    myView.setVisibility(View.VISIBLE);
                }

            }
        }, 900);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void progressBarValue(int progress) {

    }

    @Override
    public void reservationStatus(boolean status) {

    }
}

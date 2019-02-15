package com.example.android.kfupmsocialspace.contract;

import android.net.Uri;

import com.example.android.kfupmsocialspace.model.MarketItem;

public class MarketitemContract {


    public interface IView{

        void progressBarValue(int progress);

    }


    public interface IPresenter{

        void uploadItemImage(String name, Uri uri, String itemName, String price, String category, String itemDescription );
        void uploadMarketItem(MarketItem marketItem);

    }


    public interface IModel{


    }

}

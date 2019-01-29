package com.example.android.kfupmsocialspace.model;

public class UserContract {



    public interface IView{

        void onDataRecived(String userData);

    }


    public interface IPresenter{

        void onSendClick();

    }


    public interface IModel{

        String getUserFullName();

    }
}

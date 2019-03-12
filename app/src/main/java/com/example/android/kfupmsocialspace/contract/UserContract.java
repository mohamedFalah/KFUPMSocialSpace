package com.example.android.kfupmsocialspace.contract;

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

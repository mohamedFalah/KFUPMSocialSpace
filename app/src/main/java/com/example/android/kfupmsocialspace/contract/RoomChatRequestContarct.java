package com.example.android.kfupmsocialspace.contract;

import java.util.ArrayList;

public class RoomChatRequestContarct {


    public interface IView{

        void DepartmentData(ArrayList<String> departments);


    }


    public interface IPresenter{

        void getDepartmentsList();

    }


    public interface IModel{


    }


}

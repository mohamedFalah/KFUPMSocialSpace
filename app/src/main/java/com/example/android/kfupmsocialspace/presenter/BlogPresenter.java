package com.example.android.kfupmsocialspace.presenter;

import android.text.format.DateFormat;

import com.example.android.kfupmsocialspace.contract.blogContract;
import com.example.android.kfupmsocialspace.model.Blog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BlogPresenter implements blogContract.IPresenter{


     private Blog blog;
     private blogContract.IView View;

     private FirebaseDatabase database = FirebaseDatabase.getInstance();
     private DatabaseReference dbRef = database.getReference("Blog");

     //user presenter
     private userPresenter userpresenter;

    public BlogPresenter(blogContract.IView newView){

        userpresenter = new userPresenter();


        View = newView;

    }


    public void uploadBlog(String title, String content, String category){

        String writer  = userpresenter.userModel.getUserFullName();
        String writerID = userpresenter.getUserID();
        blog = new Blog(title,category,content,writer,writerID,getCurrentDate());


        //blog ID
        DatabaseReference push = dbRef.push();
        String blogID = push.getKey();

        //add the blog
        dbRef.child(category).child(blogID).setValue(blog);




    }



    private boolean checkEmptyText(String message){

        if(!message.isEmpty() && message.trim().length() > 0)
            return true;
        return false;
    }


    //get the date of the today;
    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        Date date = new Date();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String month  = (String) DateFormat.format("MMM",  date); // Jun
        String year         = (String) DateFormat.format("yyyy", date); //2013

        String time = (String) format.format(calendar.getTime());

        String currentDate = time + " "+ dayOfTheWeek + " " + day + " " + month + " " + year;

        return currentDate;
    }





}

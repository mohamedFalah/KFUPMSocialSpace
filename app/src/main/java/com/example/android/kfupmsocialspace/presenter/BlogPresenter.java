package com.example.android.kfupmsocialspace.presenter;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.example.android.kfupmsocialspace.contract.blogContract;
import com.example.android.kfupmsocialspace.model.Blog;
import com.example.android.kfupmsocialspace.model.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BlogPresenter implements blogContract.IPresenter {


    private Blog blog;
    private blogContract.IView View;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Blog");
    private DatabaseReference dbRef2 = database.getReference("Comment");

    //user presenter
    private userPresenter userpresenter;

    public BlogPresenter(blogContract.IView newView) {

        userpresenter = new userPresenter();


        View = newView;

    }


    public void uploadBlog(String title, String content, String category) {

        String writer = userpresenter.userModel.getUserFullName();
        String writerID = userpresenter.getUserID();
        blog = new Blog(title, category, content, writer, writerID, getCurrentDate());


        //blog ID
        DatabaseReference push = dbRef.push();
        String blogID = push.getKey();

        blog.setBlog_id(blogID);
        //add the blog
        dbRef.child(category).child(blogID).setValue(blog);


    }


    public void sendComment(String commentText, String blogID) {

        if (checkEmptyText(commentText)) {
            DatabaseReference push = dbRef2.push();
            String commentID = push.getKey();

            Comment comment = new Comment(blogID, commentText, userpresenter.getUserID(), userpresenter.getTheUsername(), getCurrentDate());

            comment.setCommentID(commentID);

            dbRef2.child(blogID).child(commentID).setValue(comment);
        }


    }


    private boolean checkEmptyText(String message) {

        if (!message.isEmpty() && message.trim().length() > 0)
            return true;
        return false;
    }


    //get the date of the today;
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day = (String) DateFormat.format("dd", date); // 20
        String month = (String) DateFormat.format("MMM", date); // Jun
        String year = (String) DateFormat.format("yyyy", date); //2013

        String time = (String) format.format(calendar.getTime());

        String currentDate = time + " " + month + " " + year;

        return currentDate;
    }


    ///get user blogs

    public void getUserBlogs() {


        final List<Blog> myBlogsList = new ArrayList<>();


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    blog = snapshot.getValue(Blog.class);
                    if (blog.getWriterID().equals(userpresenter.getUserID())) {
                        myBlogsList.add(blog);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        View.myblogs(myBlogsList);
    }
}

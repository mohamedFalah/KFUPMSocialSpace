package com.example.android.kfupmsocialspace.model;

public class Comment {


    private String blogID;
    private String commentID;
    private String content;
    private String userID;
    private String userName;
    private String time;

    public Comment(){

    }

    public Comment(String BlogID, String content, String userID, String userName, String time) {

        this.commentID = commentID;
        this.blogID = BlogID;
        this.content = content;
        this.userID = userID;
        this.userName = userName;
        this.time = time;

    }


    public String getUserID() { return userID; }

    public String getBlogID() { return blogID; }

    public String getCommentID() { return commentID; }

    public String getContent() { return content; }

    public String getTime() { return time; }

    public String getUserName() { return userName; }

    public void setUserID(String userID) { this.userID = userID; }

    public void setTime(String time) { this.time = time; }

    public void setBlogID(String blogID) { this.blogID = blogID; }

    public void setCommentID(String commentID) { this.commentID = commentID; }

    public void setContent(String content) { this.content = content; }

    public void setUserName(String userName) { this.userName = userName; }
}

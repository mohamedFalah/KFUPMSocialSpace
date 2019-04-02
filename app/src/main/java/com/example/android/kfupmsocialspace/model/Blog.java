package com.example.android.kfupmsocialspace.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Blog implements Parcelable {

    private String blog_id;
    private String title;
    private String category;
    private String subject;
    private String writer;
    private String writerID;
    private String time;

    public Blog() {
    }

    public Blog(String title, String category, String subject, String writer) {
        this.blog_id = blog_id;
        this.title = title;
        this.category = category;
        this.subject = subject;
        this.writer = writer;
    }

    public Blog(String title, String category, String subject, String writer, String writerID, String time) {
        this.blog_id = blog_id;
        this.title = title;
        this.category = category;
        this.subject = subject;
        this.writer = writer;
        this.writerID = writerID;
        this.time = time;
    }

    public Blog(String blog_id, String title, String category, String subject, String writer, String writerID, String time) {
        this.blog_id = blog_id;
        this.title = title;
        this.category = category;
        this.subject = subject;
        this.writer = writer;
        this.writerID = writerID;
        this.time = time;
    }

//    public int getBlog_id() {
//        return blog_id;
//    }
//
//    public void setBlog_id(int blog_id) {
//        this.blog_id = blog_id;
//    }

    protected Blog(Parcel in) {
        blog_id = in.readString();
        title = in.readString();
        category = in.readString();
        subject = in.readString();
        writer = in.readString();
        writerID = in.readString();
        time = in.readString();
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    public String getBlog_id() { return blog_id; }

    public void setBlog_id(String blog_id) { this.blog_id = blog_id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getWriterID() { return writerID; }

    public void setWriterID(String writerID) { this.writerID = writerID; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(blog_id);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(subject);
        dest.writeString(writer);
        dest.writeString(writerID);
        dest.writeString(time);

    }
}

package com.example.android.kfupmsocialspace.model;

public class Blog {

//    private int blog_id;
    private String blog_title;
    private String blog_category;
    private String blog_subject;
    private String blog_writer;

    public Blog() {
    }

    public Blog(String blog_title, String blog_category, String blog_subject, String blog_writer) {
//        this.blog_id = blog_id;
        this.blog_title = blog_title;
        this.blog_category = blog_category;
        this.blog_subject = blog_subject;
        this.blog_writer = blog_writer;
    }

//    public int getBlog_id() {
//        return blog_id;
//    }
//
//    public void setBlog_id(int blog_id) {
//        this.blog_id = blog_id;
//    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_category() {
        return blog_category;
    }

    public void setBlog_category(String blog_category) {
        this.blog_category = blog_category;
    }

    public String getBlog_subject() {
        return blog_subject;
    }

    public void setBlog_subject(String blog_subject) {
        this.blog_subject = blog_subject;
    }

    public String getBlog_writer() {
        return blog_writer;
    }

    public void setBlog_writer(String blog_writer) {
        this.blog_writer = blog_writer;
    }
}

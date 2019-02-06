package com.example.android.kfupmsocialspace.model;

import com.example.android.kfupmsocialspace.contract.RoomChatRequestContarct;

public class CoursesData implements RoomChatRequestContarct.IModel {


    private String CourseName;
    private String CourseTitle;


    public CoursesData(){

    }

    public CoursesData(String CourseName, String CourseTitle){

        this.CourseName = CourseName;
        this.CourseTitle = CourseTitle;



    }


    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }


}

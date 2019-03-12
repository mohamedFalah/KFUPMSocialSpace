package com.example.android.kfupmsocialspace;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.RoomChatRequestContarct;
import com.example.android.kfupmsocialspace.presenter.RoomChatRequestPresenter;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity implements RoomChatRequestContarct.IView, View.OnClickListener {


    public String departmentSpinnerValue;
    public String CourseSpinnerValue;
    private RoomChatRequestPresenter roomChatRequestPresenter;
    private ArrayList<String> Departments = new ArrayList<String>();
    private ArrayList<String> Courses = new ArrayList<String>();
    private Spinner departmentSpinner;
    private Spinner departmentCoursesSpinner;
    private Button RequestButton;
    private ProgressDialog progressDialog;


    private ArrayAdapter<String> departmentsAdapter;
    private ArrayAdapter<String> CoursesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        roomChatRequestPresenter = new RoomChatRequestPresenter(this);
        roomChatRequestPresenter.getDepartmentsList();

        departmentSpinner = findViewById(R.id.departments_spinner);
        departmentCoursesSpinner = findViewById(R.id.department_courses_spinner);
        RequestButton = findViewById(R.id.request_button);

        RequestButton.setOnClickListener(this);


        roomChatRequestPresenter.Departments.add("choose department");
        roomChatRequestPresenter.coursesModel.add("choose course");

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading data");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.hide();

            }
        }, 5666);


        departmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomChatRequestPresenter.Departments);
        departmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentsAdapter);


        CoursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomChatRequestPresenter.coursesModel);
        CoursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentCoursesSpinner.setAdapter(CoursesAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int postion, long arg3) {
                departmentSpinnerValue = parent.getItemAtPosition(postion).toString();
                roomChatRequestPresenter.CoursesList(departmentSpinnerValue);

                progressDialog.setMessage("Loading data");
                progressDialog.show();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.hide();

                    }
                }, 2000);

                CoursesAdapter.clear();
                roomChatRequestPresenter.coursesModel.add("Choose course");
                CoursesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        departmentCoursesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int postion, long arg3) {
                CourseSpinnerValue = parent.getItemAtPosition(postion).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void DepartmentData(ArrayList<String> departments) {

        Departments.addAll(departments);


    }

    @Override
    public void onClick(View v) {

        if (CourseSpinnerValue != null) {
            roomChatRequestPresenter.CreateRoomchatJoinRequest(CourseSpinnerValue);
            Toast.makeText(this, "requested", Toast.LENGTH_SHORT).show();
            this.finish();
        } else
            Toast.makeText(this, "Choose course and department", Toast.LENGTH_SHORT).show();
    }
}

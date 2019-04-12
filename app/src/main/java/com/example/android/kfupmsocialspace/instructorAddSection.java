package com.example.android.kfupmsocialspace;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.RoomChatRequestContarct;
import com.example.android.kfupmsocialspace.presenter.RoomChatCreatePresenter;

import java.util.ArrayList;

public class instructorAddSection extends AppCompatActivity implements RoomChatRequestContarct.IView, View.OnClickListener {


    public String departmentSpinnerValue;
    public String CourseSpinnerValue;
    public String sectionNumValue;
    public String studentsIDsValue;
    private RoomChatCreatePresenter roomChatCreatePresenter;
    private ArrayList<String> Departments = new ArrayList<String>();
    private ArrayList<String> Courses = new ArrayList<String>();
    private Spinner departmentSpinner;
    private Spinner departmentCoursesSpinner;
    private EditText sectionNum;
    private EditText studentIDs;
    private Button CreateButton;
    private ProgressDialog progressDialog;


    private ArrayAdapter<String> departmentsAdapter;
    private ArrayAdapter<String> CoursesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_add_section);

        roomChatCreatePresenter = new RoomChatCreatePresenter(this);
        roomChatCreatePresenter.getDepartmentsList();

        departmentSpinner = findViewById(R.id.departments_spinner);
        departmentCoursesSpinner = findViewById(R.id.department_courses_spinner);
        sectionNum = findViewById(R.id.editText);
        studentIDs = findViewById(R.id.editText3);
        CreateButton = findViewById(R.id.create_button);

        CreateButton.setOnClickListener(this);


        roomChatCreatePresenter.Departments.add("choose department");
        roomChatCreatePresenter.coursesModel.add("choose course");

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading data");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.hide();

            }
        }, 5666);


        departmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomChatCreatePresenter.Departments);
        departmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentsAdapter);


        CoursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomChatCreatePresenter.coursesModel);
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
                roomChatCreatePresenter.CoursesList(departmentSpinnerValue);

                progressDialog.setMessage("Loading data");
                progressDialog.show();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.hide();

                    }
                }, 2000);

                CoursesAdapter.clear();
                roomChatCreatePresenter.coursesModel.add("Choose course");
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
        sectionNumValue = sectionNum.getText().toString();
        studentsIDsValue = studentIDs.getText().toString();

        if (CourseSpinnerValue != null &&  sectionNumValue != null && studentsIDsValue != null && !CourseSpinnerValue.equals("Choose course")) {
            roomChatCreatePresenter.CreateRoomchat(CourseSpinnerValue ,sectionNumValue , studentsIDsValue );
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
            this.finish();
        } else
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
    }
}

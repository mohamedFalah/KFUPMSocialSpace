package com.example.android.kfupmsocialspace.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.kfupmsocialspace.contract.RoomChatRequestContarct;
import com.example.android.kfupmsocialspace.contract.UserContract;
import com.example.android.kfupmsocialspace.model.CoursesData;
import com.example.android.kfupmsocialspace.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class RoomChatRequestPresenter implements RoomChatRequestContarct.IPresenter {


    //firebase connection
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Courses");
    private DatabaseReference RoomChat = database.getReference("Chat");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    //get the user ID
    private String UserID = "";



    // load the departments and courses in arraylists
    public ArrayList<String> Departments = new ArrayList<String>();;
    public ArrayList<String> coursesModel = new ArrayList<>();


    //variables declaritionn
    CoursesData Course;
    RoomChatRequestContarct.IView view;




    //constructor
    public RoomChatRequestPresenter(RoomChatRequestContarct.IView newView){
        if(mAuth.getCurrentUser() != null)
            UserID = mAuth.getCurrentUser().getUid();

        DepartmentList();
        view = newView;

    }


    /*******************

     /getting the list of departments

     ********************/

    //getting the list of departments
    private void DepartmentList(){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot department: dataSnapshot.getChildren()) {
                    String child = department.getKey();

                    Departments.add(child);
                }

                Log.d(TAG,"sizesizesizesizesizesizesize presenter" +Departments.size());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled", databaseError.toException());
            }});

    }

    /*******************

     get the list of courses in each department

     ********************/


    ///get the list of courses in each department
    public void CoursesList(String department){
        dbRef.child(department).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot course: dataSnapshot.getChildren()) {

                    Course = course.getValue(CoursesData.class);
                    coursesModel.add(Course.getCourseName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled", databaseError.toException());
            }});

    }



    /*******************

     CREATE REQUESTS DONE IN THIS METHOD

     ********************/

    public void CreateRoomchatJoinRequest(final String course){

        RoomChat.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(course)) {

                    RoomChat.child(course).child("Requests").child(UserID);


                }
                else{
                    DatabaseReference push = dbRef.push();
                    String push_Id = push.getKey();


                    //Arranging the structure of the data
                    Map Request = new HashMap();
                    Request.put("StudentID", UserID);

                    Map map2 = new HashMap();
                    map2.put("Requests/" + push_Id, Request);

                    RoomChat.child(course).updateChildren(map2, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d("CHAT_LOG", databaseError.getMessage());
                            }
                        }
                    });

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled", databaseError.toException());
            }});


    }

    /*******************

     INTERFACE METHOD THAT DOESNT WORK CORRECTLY UNTIL NOW

     ********************/

    @Override
    public void getDepartmentsList() {


        view.DepartmentData(Departments);


    }

}

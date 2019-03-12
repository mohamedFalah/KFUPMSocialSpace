package com.example.android.kfupmsocialspace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    //references to the view components
    private EditText IDNumber;
    private EditText password;
    private EditText FirstName;
    private EditText LastName;
    private EditText phone;

    private Button SignUp;
    private TextView SignIn;

    private ProgressDialog progressDialog;

    //reference to the firebase auth and database
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initiate the firebase things!!
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //dialog to tell the user what is going on
        progressDialog = new ProgressDialog(this);


        // link the the references to the view
        IDNumber = findViewById(R.id.IDNumberSignIn);
        password = findViewById(R.id.PasswordSignIn);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        phone = findViewById(R.id.phone);
        SignUp = findViewById(R.id.SignUp);
        SignIn = findViewById(R.id.SignIn);


        //handle the clicks on view
        SignUp.setOnClickListener(this);
        SignIn.setOnClickListener(this);
    }

    // method to register the user into firebase auth
    private void registerUser() {

        //get the values of the edit boxes
        final String email = "s" + IDNumber.getText().toString().trim() + "@kfupm.edu.sa";
        String password_text = password.getText().toString().trim();
        final String first_name_text = FirstName.getText().toString().trim();
        final String last_name_text = LastName.getText().toString().trim();
        final String phoneNumber = phone.getText().toString().trim();

        //check of an empty box
        CheckFields(email, password_text, first_name_text, last_name_text);


        //show the progress bar to user
        progressDialog.setMessage("Registering User");
        progressDialog.show();

        //method to create the user from firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password_text)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //get the registered user ID
                            String userID = task.getResult().getUser().getUid();

                            //create user object
                            User user;
                            if (!TextUtils.isEmpty(phoneNumber)) {
                                user = new User(
                                        email,
                                        first_name_text,
                                        last_name_text,
                                        phoneNumber


                                );
                            } else {

                                user = new User(
                                        email,
                                        first_name_text,
                                        last_name_text

                                );

                            }

                            //add user method
                            addUser(userID, user);
                            //tell the user of the status
                            Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_LONG).show();

                            //hide the dialog bar
                            progressDialog.hide();

                            //go to the sign in page
                            GoToSignInPage();

                        } else {
                            Toast.makeText(SignUp.this, "ERROR! failed to register", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }

                });
    }



    //method to save the user information into the database

    private void addUser(String userID, User user) {

        databaseReference.child("User").child(userID).setValue(user);

    }


    //method to check the input by the user.
    private void CheckFields(String email, String password, String FirstName_text, String LastName_text) {

        //email input
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email", Toast.LENGTH_LONG).show();

            return;
        }

        // password input
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter your password", Toast.LENGTH_LONG).show();

            return;
        }

        //first name input
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter your First Name", Toast.LENGTH_LONG).show();

            return;
        }

        //last name input
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter your Last Name", Toast.LENGTH_LONG).show();

            return;
        }


    }


    //method to redirect the user to the sign in page after the registration.
    private void GoToSignInPage() {

        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    // handle clicks
    @Override
    public void onClick(View view) {

        if (view == SignUp) {

            registerUser();
        }

        if (view == SignIn) {
            this.finish();

        }
    }
}

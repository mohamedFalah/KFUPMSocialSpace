package com.example.android.kfupmsocialspace;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText IDNumber;
    private EditText password;
    private Button SignUp;
    private TextView SignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        SignUp = findViewById(R.id.SignUp);
        IDNumber = findViewById(R.id.IDNumberSignIn);
        password = findViewById(R.id.PasswordSignIn);
        SignIn = findViewById(R.id.SignIn);

        SignUp.setOnClickListener(this);
        SignIn.setOnClickListener(this);
    }

    // method to register the user into firebase auth
    private void registerUser() {
        //get the values of the editboxes
        String email = "s" + IDNumber.getText().toString().trim() + "@kfupm.edu.sa";
        String passwordtext = password.getText().toString().trim();

        //check of an empty box
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(passwordtext)) {
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
        }

        //show the prgress basr to user
        progressDialog.setMessage("Registering User");
        progressDialog.show();

        //method to create the user from firebase
        firebaseAuth.createUserWithEmailAndPassword(email, passwordtext)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_LONG).show();
                            progressDialog.hide();

                            //go to the sign in page
                            Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignUp.this, "ERROR! faild to register", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }
                });
    }

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

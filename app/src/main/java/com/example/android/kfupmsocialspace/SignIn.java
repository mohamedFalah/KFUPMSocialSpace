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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener {


    private EditText IDNumber;
    private EditText password;
    private Button SignIn;
    private TextView SignUp;
    private TextView InstructorSignIn;


    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        // check if the user already logged in
        if (firebaseUser != null) {
            if(firebaseAuth.getCurrentUser().isEmailVerified()) {

                Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(), "please verify your email address", Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        setContentView(R.layout.activity_sign_in);


        progressDialog = new ProgressDialog(this);

        SignIn = findViewById(R.id.sign_in);
        IDNumber = findViewById(R.id.id_number_sign_in);
        password = findViewById(R.id.password_sign_in);
        SignUp = findViewById(R.id.sign_up);
        InstructorSignIn = findViewById(R.id.instructor_sign_in);


        SignUp.setOnClickListener(this);
        SignIn.setOnClickListener(this);
        InstructorSignIn.setOnClickListener(this);

    }


    private void loginUser() {

        String email = "s" + IDNumber.getText().toString().trim() + "@kfupm.edu.sa";
        String password_text = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();

            return;

        } else if (TextUtils.isEmpty(password_text)) {
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();

            return;
        }


        progressDialog.setMessage("Signing In");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    if(firebaseAuth.getCurrentUser().isEmailVerified()) {

                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                   else {
                        Toast.makeText(getApplicationContext(), "please verify your email address", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view == SignIn) {
            loginUser();
        }

        if (view == SignUp) {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if (view == InstructorSignIn) {
            Intent intent = new Intent(getApplicationContext(), InstructorSignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.finish();
            startActivity(intent);

        }

    }
}

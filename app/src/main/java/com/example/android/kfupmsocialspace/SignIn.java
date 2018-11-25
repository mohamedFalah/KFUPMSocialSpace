package com.example.android.kfupmsocialspace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignIn extends AppCompatActivity implements View.OnClickListener{


    private EditText IDNumber;
    private EditText password;
    private Button SignIn;
    private TextView SignUp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        SignIn = (Button) findViewById(R.id.SignIn);
        IDNumber = (EditText) findViewById(R.id.IDNumberSignIn);
        password = (EditText) findViewById(R.id.PasswordSignIn);
        SignUp   = (TextView) findViewById(R.id.SignUp);

        SignUp.setOnClickListener(this);
        SignIn.setOnClickListener(this);

    }


    private void loginUser(){

        String email = "s" + IDNumber.getText().toString().trim() + "@kfupm.edu.sa";
        String passwordtext = password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"please enter your email", Toast.LENGTH_SHORT).show();

            return;

        }

        else if(TextUtils.isEmpty(passwordtext)){
            Toast.makeText(this,"please enter your password", Toast.LENGTH_SHORT).show();

            return;
        }


        progressDialog.setMessage("Signing In");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Logged in successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == SignIn){
            loginUser();
        }

        if(view == SignUp){
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

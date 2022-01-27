package com.example.signinactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText SignInEmail, SignInPassword;
private Button SignInBtn;
private TextView SignInTextView, forgotTextView;
private ProgressBar SignInProgressBar;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In");
        mAuth = FirebaseAuth.getInstance();
        SignInEmail=findViewById(R.id.SignInEmailEditTextID);
        SignInPassword=findViewById(R.id.SignInPasswordEditTextID);
        SignInBtn=findViewById(R.id.SignInBtnId);
        SignInTextView=findViewById(R.id.SignInTextViewId);
        SignInProgressBar=findViewById(R.id.SignInProgressId);
        forgotTextView=findViewById(R.id.ForgotId);
        forgotTextView.setOnClickListener(this);
        SignInBtn.setOnClickListener(this);
        SignInTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.SignInTextViewId:{
                Intent intent=new Intent(MainActivity.this,SingUpActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.SignInBtnId:{
                UserLogin();
                break;
            }
            case R.id.ForgotId:{
                forgot_password();
                break;
            }
        }

    }

    private void forgot_password() {
    final EditText resetMail=new EditText(forgotTextView.getContext());
        final AlertDialog.Builder PasswordResetDialog=new AlertDialog.Builder(forgotTextView.getContext());
        PasswordResetDialog.setTitle("Reset Password ?");
        PasswordResetDialog.setMessage("Enter Your Mail To Receive Reset Link");
        PasswordResetDialog.setView(resetMail);
        PasswordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=resetMail.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                   Toast.makeText(getApplicationContext(),"Reset Link Sent To Your Email",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Reset Link is not Sent" +e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        PasswordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        PasswordResetDialog.create().show();
    }

    private void UserLogin() {
        String email=SignInEmail.getText().toString().trim();
        String password=SignInPassword.getText().toString().trim();
        if (email.isEmpty()){
            SignInEmail.setError("Enter Your Password");
            SignInEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            SignInEmail.setError("Please Enter Valid Email");
            SignInEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            SignInPassword.setError("Please Enter Password");
            SignInPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            SignInPassword.setError("please Enter valid password");
            SignInPassword.requestFocus();
            return;
        }
        SignInProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                SignInProgressBar.setVisibility(View.GONE);
              if (task.isSuccessful()){
                  finish();
                  Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                  startActivity(intent);
                  Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
              } else{
                  Toast.makeText(getApplicationContext(),"Login Unsucessful",Toast.LENGTH_LONG).show();
              }
            }
        });
    }
}

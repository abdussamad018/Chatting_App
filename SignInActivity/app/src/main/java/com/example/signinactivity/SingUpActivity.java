package com.example.signinactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {
private TextView SignUpTextView;
private EditText SignUpFullName, SignUpFatherName, SignUpMotherName,SignUpEmail, SignUpPhone, SignUpAge, SignUpPassword, SignUpConfirmPassword;
private Button SignUpBtn;
private RadioButton Male,Female,Others;
private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        this.setTitle("Sign Up");
        mAuth = FirebaseAuth.getInstance();

        SignUpTextView=findViewById(R.id.SignUpTextViewId);
        SignUpFullName=findViewById(R.id.SignUpFullNameEditTextID);
        SignUpFatherName=findViewById(R.id.SignUpFathersNameEditTextID);
        SignUpMotherName=findViewById(R.id.SignUpMothersNameEditTextID);
        SignUpEmail=findViewById(R.id.SignUpEmailEditTextID);
        SignUpPhone=findViewById(R.id.SignUpPhoneEditTextID);
        SignUpAge=findViewById(R.id.SignUpAgeEditTextID);
        SignUpPassword=findViewById(R.id.SignUpPasswordEditTextID);
        SignUpConfirmPassword=findViewById(R.id.SignUpConfirmPasswordEditTextID);
        SignUpBtn=findViewById(R.id.SignUpBtnId);
        Male=findViewById(R.id.SignUpMale);
       Female=findViewById(R.id.SignUpFemale);
        Others=findViewById(R.id.SignUpOthers);
        progressBar=findViewById(R.id.SignUpProgressId);
        SignUpTextView=findViewById(R.id.SignUpTextViewId);
        SignUpBtn.setOnClickListener(this);
        SignUpTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUpTextViewId:
            {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.SignUpBtnId:{
                UserRegistration();
                break;
            }
        }
        
    }

    private void UserRegistration() {
        String Name=SignUpFullName.getText().toString().trim();
        String FName=SignUpFatherName.getText().toString().trim();
        String MName=SignUpMotherName.getText().toString().trim();
        String PhoneNumber=SignUpPhone.getText().toString().trim();
        String Age=SignUpAge.getText().toString().trim();
        String email=SignUpEmail.getText().toString().trim();
        String password=SignUpPassword.getText().toString().trim();
        String ConfirmPassword=SignUpConfirmPassword.getText().toString().trim();


        if (Name.isEmpty()){
            SignUpFullName.setError("Please Enter Your Full Name");
            SignUpFullName.requestFocus();
            return;

        }
        if (FName.isEmpty()){
            SignUpFatherName.setError("Please Enter Your Fathers Name");
            SignUpFatherName.requestFocus();
            return;

        }
        if (MName.isEmpty()){
            SignUpMotherName.setError("Please Enter Your Mothers Name");
            SignUpMotherName.requestFocus();
            return;
        }
        if (PhoneNumber.isEmpty()){
            SignUpPhone.setError("Please Enter Your Phone Number");
            SignUpPhone.requestFocus();
            return;
        }
        if (email.isEmpty()){
            SignUpEmail.setError("Please Enter Your Email");
            SignUpEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            SignUpEmail.setError("Please Enter Valid Email");
            SignUpEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            SignUpPassword.setError("Please Enter Password");
            SignUpPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            SignUpPassword.setError("please Enter valid password");
            SignUpPassword.requestFocus();
            return;
        }
        if (ConfirmPassword.isEmpty()){
            SignUpConfirmPassword.setError("Enter Confirm Password");
            SignUpConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(ConfirmPassword)){
            SignUpConfirmPassword.setError("Do not Match Confirm Password");
            SignUpConfirmPassword.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    finish();
                    Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Registation Successful",Toast.LENGTH_LONG).show();
                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(getApplicationContext(),"User Already Registation",Toast.LENGTH_LONG).show();
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(),"Registation Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}

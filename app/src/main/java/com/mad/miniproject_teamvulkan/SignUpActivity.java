package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    /*full_name, phone_no,*/
    private EditText  email, password;
    private Button sign_up_btn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.input_sign_up_email);
        password = findViewById(R.id.input_sign_up_password);
        sign_up_btn = findViewById(R.id.btn_sign_up2);

        mAuth = FirebaseAuth.getInstance();

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    private void createUser(){

        String sign_up_email = email.getText().toString();
        String sign_up_password = password.getText().toString();

        if(!sign_up_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(sign_up_email).matches()){
            if(!sign_up_password.isEmpty()){
                mAuth.createUserWithEmailAndPassword(sign_up_email,sign_up_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "User Registration Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this,"User Registration Unsuccessful! Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                password.setError("Password field is empty.");
            }
        }else if(sign_up_email.isEmpty()){
            email.setError("Email field is empty.");
        }else {
            email.setError("Please enter a valid email.");
        }
    }

}
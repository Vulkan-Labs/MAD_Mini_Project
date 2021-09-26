package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText  full_name, phone_no, email, password;
    private RadioButton radioCustomer, radioPharmacy;
    private Button sign_up_btn;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    String fullName, phoneNo;
    String acc_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        full_name = findViewById(R.id.input_full_name);
        phone_no = findViewById(R.id.input_phone_no);
        email = findViewById(R.id.input_sign_up_email);
        password = findViewById(R.id.input_sign_up_password);
        radioCustomer = findViewById(R.id.rb_customer);
        radioPharmacy = findViewById(R.id.rb_pharmacy);

        sign_up_btn = findViewById(R.id.btn_sign_up2);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserDetails");

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    private void createUser() {

        String sign_up_email = email.getText().toString();
        String sign_up_password = password.getText().toString();
        fullName = full_name.getText().toString();
        phoneNo = phone_no.getText().toString();

        if (radioCustomer.isChecked()) acc_type = "Customer";
        else if (radioPharmacy.isChecked()) acc_type = "Pharmacy";
        else acc_type = "";

        if (ValidateFullName() && ValidatePhoneNo() && ValidateAccType()) {
            if (!sign_up_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(sign_up_email).matches()) {
                if (!sign_up_password.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(sign_up_email, sign_up_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        UserDetails user = new UserDetails(fullName, phoneNo, sign_up_email, acc_type);

                                        FirebaseDatabase.getInstance().getReference("UserDetails")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(SignUpActivity.this, "User Registration Successful!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                        finish();
                                                    }
                                                });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "User Registration Unsuccessful! Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    password.setError("Password field is empty.");
                }
            } else if (sign_up_email.isEmpty()) {
                email.setError("Email field is empty.");
            } else {
                email.setError("Please enter a valid email.");
            }
        }else
            Toast.makeText(SignUpActivity.this, "User Registration Unsuccessful! Try Again!", Toast.LENGTH_SHORT).show();
    }

    //validation functions for full name, phone number & account type

    private boolean ValidateFullName(){
        if(!TextUtils.isEmpty(fullName)){
            return true;
        }else {
            Toast.makeText(SignUpActivity.this,"Full Name field is empty, please enter your full name.",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean ValidatePhoneNo(){
        if(!TextUtils.isEmpty(phoneNo)){
            return true;
        }else {
            Toast.makeText(SignUpActivity.this,"Phone Number field is empty, please enter your phone number.",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean ValidateAccType() {
        if(!TextUtils.isEmpty(acc_type)){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Please select an Account Type",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
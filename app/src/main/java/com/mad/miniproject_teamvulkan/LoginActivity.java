package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView forgotPassword;
    private Button login_btn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);

        forgotPassword = findViewById(R.id.txt_forgot_password);

        login_btn = findViewById(R.id.btn_login2);

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResetPasswordActivity();
            }
        });

    }

    private void loginUser(){

        String login_email = email.getText().toString();
        String login_password = password.getText().toString();
        //final String[] accType = new String[1];
        //String accTypeString;

        if(!login_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(login_email).matches()){
            if(!login_password.isEmpty()){
                mAuth.signInWithEmailAndPassword(login_email,login_password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                //Getting User Account Type from DB

                                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(userID);
                                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (snapshot.hasChild("accountType")) {
                                            String accTypeString = snapshot.child("accountType").getValue(String.class);
                                            //For Testing Purposes Only
                                            //Toast.makeText(LoginActivity.this,userID,Toast.LENGTH_SHORT).show();

                                            if(accTypeString.equals("Pharmacy")){
                                                Toast.makeText(LoginActivity.this, "Logged In Successfully!",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(LoginActivity.this, pharmacyMain.class));
                                                finish();
                                            }else if(accTypeString.equals("Customer")){
                                                Toast.makeText(LoginActivity.this, "Logged In Successfully!",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(LoginActivity.this, AddCategoryActivity.class));
                                                finish();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"Login Attempt Unsuccessful! Try Again!",Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                password.setError("Password field is empty!");
            }
        }else if(login_email.isEmpty()){
            email.setError("Email field is empty!");
        }else{
            email.setError("Please enter a valid email address!");
        }
    }

    public void openResetPasswordActivity(){
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}
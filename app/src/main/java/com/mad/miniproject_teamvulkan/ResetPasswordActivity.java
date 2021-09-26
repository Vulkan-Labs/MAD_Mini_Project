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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText reset_email;
    private Button reset_password_btn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        reset_email = findViewById(R.id.input_reset_password_email);
        reset_password_btn = findViewById(R.id.btn_reset_password);

        mAuth = FirebaseAuth.getInstance();

        reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResetPasswordLink();
            }
        });

    }

    private void sendResetPasswordLink() {

        String email = reset_email.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ResetPasswordActivity.this, "Reset Password Email Sent Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
        }else if(email.isEmpty()) {
            reset_email.setError("Email field is empty!");
        }else {
            Toast.makeText(ResetPasswordActivity.this, "Reset Password Failed, try again.", Toast.LENGTH_SHORT).show();
        }

    }

}
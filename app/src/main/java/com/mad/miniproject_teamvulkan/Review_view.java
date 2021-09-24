package com.mad.miniproject_teamvulkan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Review_view extends AppCompatActivity {

    EditText textname, textcomment;
    Button btn_submit;
    DatabaseReference dbref;
    reviews rev;
    Button show_all;

    private void clearControls() {
        textname.setText("");
        textcomment.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_view);

        textname = findViewById(R.id.rev_name);
        textcomment = findViewById(R.id.comment_text);

        btn_submit = findViewById(R.id.submit_btn_rev);
        show_all = findViewById(R.id.all_show);

        rev = new reviews();

        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Review_view.this, Retriev_rev.class);
                startActivity(intent);
            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("Review");
                try {
                    if (TextUtils.isEmpty(textname.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(textcomment.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();


                    else {
                        rev.setName(textname.getText().toString().trim());
                        rev.setComment(textcomment.getText().toString());

//                        dbref.push().setValue(rev);
                        dbref.child("rev1").setValue(rev);

                        Toast.makeText(getApplicationContext(), "Data Successfully added", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
                }
            }

            ;
        });

    }
}
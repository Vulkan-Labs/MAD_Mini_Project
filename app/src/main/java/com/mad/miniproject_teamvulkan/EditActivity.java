package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    EditText up_name , up_comment;
    Button save_btn , cancel_btn;
    DatabaseReference dbref;
    reviews rev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        up_name = findViewById(R.id.up_name);
        up_comment = findViewById(R.id.up_comment);

        save_btn = findViewById(R.id.save_update_btn);
        cancel_btn = findViewById(R.id.cancel_update_btn);

        rev = new reviews();


        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Review").child("rev1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    up_name.setText(snapshot.child("name").getValue().toString());
                    up_comment.setText(snapshot.child("comment").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, Retriev_rev.class);
                startActivity(intent);

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Review");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("rev1")){
                            try {
                                rev.setName(up_name.getText().toString().trim());
                                rev.setComment(up_comment.getText().toString().trim());

                                dbref = FirebaseDatabase.getInstance().getReference().child("Review").child("rev1");
                                dbref.setValue(rev);

                                Toast.makeText(getApplicationContext(), "Data Update Successful", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
}
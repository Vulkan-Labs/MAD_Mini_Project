package com.mad.miniproject_teamvulkan;

//import static com.mad.miniproject_teamvulkan.Review_adapter.EXTRA_MESSAGE;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    EditText revId , up_name , up_comment;
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

        Intent intent = getIntent();
        String prid = intent.getStringExtra(EXTRA_MESSAGE);

        TextView txtMessage = findViewById(R.id.revId_view);
        txtMessage.setText(prid);

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Review").child(prid);
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
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Review");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(prid)){
                                rev.setRevID(prid);
                                rev.setName(up_name.getText().toString().trim());
                                rev.setComment(up_comment.getText().toString().trim());

                                dbref = FirebaseDatabase.getInstance().getReference().child("Review").child(prid);
                                dbref.setValue(rev);

                                Toast.makeText(getApplicationContext(), "Data Update Successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(EditActivity.this , Retriev_rev.class));
                                finish();

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
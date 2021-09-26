package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Review_view extends AppCompatActivity {

    EditText textname, textcomment;
    TextView reviewId;
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
        reviewId = findViewById(R.id.review_id);

        btn_submit = findViewById(R.id.submit_btn_rev);
        show_all = findViewById(R.id.all_show);

        rev = new reviews();


        //new try

        dbref = FirebaseDatabase.getInstance().getReference().child("Review");
        Query readRef =  FirebaseDatabase.getInstance().getReference().child("Review").limitToLast(1);
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.hasChildren()) {

                    for(DataSnapshot ds: datasnapshot.getChildren()){

                        String revID = ds.child("revID").getValue().toString();

                        if(revID.equals("")){
                            revID = "REV0000";
                        }
                        String rev_IDnum =  revID.substring(3,7);

                        int IDconvert = Integer.valueOf(rev_IDnum);
                        int nextID = IDconvert + 1;

                        String nextIDPrefix = "REV";
                        String nextIDSuffix = ("0000" + nextID);
                        String nextIDSuffixTrimmed = nextIDSuffix.substring(nextIDSuffix.length() - 4);
                        String final_revID = nextIDPrefix + nextIDSuffixTrimmed;

                        TextView txtMessage = findViewById(R.id.review_id);

//                         txtMessage.setText(Integer.toString(nextID));

                        txtMessage.setText("PRODUCT ID " + final_revID);
//                        reviewId.setText(final_revID);
                        rev.setRevID(final_revID);

                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Database Empty ID Initilised", Toast.LENGTH_SHORT).show();

                    String ID = "REV0000";

                    String IDnum =  ID.substring(3,7);

                    int IDconvert = Integer.valueOf(IDnum);
                    int nextID = IDconvert + 1;

                    String nextIDPrefix = "REV";
                    String nextIDSuffix = ("0000" + nextID);
                    String nextIDSuffixTrimmed = nextIDSuffix.substring(nextIDSuffix.length() - 4);
                    String final_revID = nextIDPrefix + nextIDSuffixTrimmed;

                    TextView txtMessage = findViewById(R.id.review_id);

                    // txtMessage.setText(Integer.toString(nextID));

                   txtMessage.setText("PRODUCT ID " + final_revID);
//                    reviewId.setText(final_revID);
                    rev.setRevID(final_revID);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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
                        dbref.child(rev.getRevID()).setValue(rev);

                        Toast.makeText(getApplicationContext(), "Data Successfully added", Toast.LENGTH_SHORT).show();
                        clearControls();

                        Intent allrev = new Intent(Review_view.this , Retriev_rev.class);
                        startActivity(allrev);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
                }
            }

            ;
        });

    }
}
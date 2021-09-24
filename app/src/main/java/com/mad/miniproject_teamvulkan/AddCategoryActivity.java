package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddCategoryActivity extends AppCompatActivity {

    EditText txt_category_ID, txt_category_name, txt_category_description;
    Button btnAdd;
    DatabaseReference dbRef;

    private void clearControls() {
        txt_category_ID.setText("");
        txt_category_name.setText("");
        txt_category_description.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        txt_category_ID = findViewById(R.id.txt_category_id);
        txt_category_name = findViewById(R.id.input_category_name);
        txt_category_description = findViewById(R.id.input_category_description);

        txt_category_ID = findViewById(R.id.txt_category_id);
        btnAdd = findViewById(R.id.btn_add_category);

        Category ctg = new Category();


        dbRef = FirebaseDatabase.getInstance().getReference().child("Category");

        Query readRef = FirebaseDatabase.getInstance().getReference().child("Category").orderByKey().limitToLast(1);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.hasChildren()) {
                    //Category ID Generation & Assignment
                    for (DataSnapshot ds: datasnapshot.getChildren()) {

                        String CID = ds.child("category_ID").getValue().toString();

                        if (CID.equals("")) {
                            CID = "CT000";
                        }

                        String CID_number = CID.substring(2, 5);

                        int CID_convert = Integer.parseInt(CID_number);
                        int next_CID = CID_convert + 1;

                        String next_CID_prefix = "CT";
                        String next_CID_suffix = ("000" + next_CID);
                        String next_CID_suffix_trimmed = next_CID_suffix.substring(next_CID_suffix.length() - 3);
                        String final_CID = next_CID_prefix + next_CID_suffix_trimmed;

                        txt_category_ID.setText(final_CID);
                        ctg.setCategory_ID(final_CID);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No data in DB, initialising new record.", Toast.LENGTH_SHORT).show();

                    String CID = "CT000";

                    String CID_number = CID.substring(2, 5);

                    int CID_convert = Integer.parseInt(CID_number);
                    int next_CID = CID_convert + 1;

                    String next_CID_prefix = "CT";
                    String next_CID_suffix = "000" + next_CID;
                    String next_CID_suffix_trimmed = next_CID_suffix.substring(next_CID_suffix.length() - 6);
                    String final_CID = next_CID_prefix + next_CID_suffix_trimmed;

                    txt_category_ID.setText(final_CID);
                    ctg.setCategory_ID(final_CID);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                try {
                    if (TextUtils.isEmpty((txt_category_name).getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Category Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty((txt_category_description).getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Category Description", Toast.LENGTH_SHORT).show();
                    else {
                        //assigning User Inputs to Instance of Category
                        ctg.setCategory_name(txt_category_name.getText().toString().trim());
                        ctg.setCategory_description(txt_category_description.getText().toString().trim());
                        //inserting values to DB
                        dbRef.child(ctg.getCategory_ID()).setValue(ctg);
                        //toast message to user
                        Toast.makeText(getApplicationContext(), "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
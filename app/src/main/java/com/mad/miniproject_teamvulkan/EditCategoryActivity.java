package com.mad.miniproject_teamvulkan;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
import com.google.firebase.database.ValueEventListener;

public class EditCategoryActivity extends AppCompatActivity {

    EditText txt_category_ID, txt_category_name, txt_category_description;
    Button btnEdit;
    DatabaseReference dbRef;
    Category ctg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        txt_category_ID = findViewById(R.id.txt_category_id_display);
        txt_category_name = findViewById(R.id.input_category_name_edit);
        txt_category_description = findViewById(R.id.input_category_description_edit);

        btnEdit = findViewById(R.id.btn_edit_category);

        Intent intent = getIntent();
        String category_id = intent.getStringExtra(EXTRA_MESSAGE);

        ctg = new Category();

        txt_category_ID.setText(category_id);

        DatabaseReference readRef  = FirebaseDatabase.getInstance().getReference().child("Category").child(category_id);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){
                    txt_category_name.setText(snapshot.child("category_name").getValue().toString());
                    txt_category_description.setText(snapshot.child("category_description").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference dbEditRef = FirebaseDatabase.getInstance().getReference().child("Category");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbEditRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(category_id)){
                            try {
                                if(TextUtils.isEmpty(txt_category_name.getText().toString()))
                                    Toast.makeText(getApplicationContext(),"Category Name field Empty!",Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txt_category_description.getText().toString()))
                                    Toast.makeText(getApplicationContext(),"Category Description field is Empty!",Toast.LENGTH_SHORT).show();
                                else {
                                    ctg.setCategory_ID(category_id);
                                    ctg.setCategory_name(txt_category_name.getText().toString().trim());
                                    ctg.setCategory_description(txt_category_description.getText().toString());

                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Category").child(category_id);
                                    dbRef.setValue(ctg);

                                    Toast.makeText(getApplicationContext(),"Edited Category Details successfully!",Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(EditCategoryActivity.this, ManageCategoriesActivity.class));
                                    finish();
                                }
                            }catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(),"NumberFormatException",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}
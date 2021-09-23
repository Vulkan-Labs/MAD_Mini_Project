package com.mad.miniproject_teamvulkan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        Category ctg = new Category();

        btnAdd = findViewById(R.id.btn_add_category);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Category");

        Button addCategoryBtn = findViewById(R.id.btn_add_category);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                try {
                    if (TextUtils.isEmpty(txt_category_ID.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Category ID", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty((txt_category_name).getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Category Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty((txt_category_description).getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Category Description", Toast.LENGTH_SHORT).show();
                    else {
                        //assigning User Inputs to Instance of Category
                        ctg.setCategory_ID(txt_category_ID.getText().toString().trim());
                        ctg.setCategory_name(txt_category_name.getText().toString().trim());
                        ctg.setCategory_description(txt_category_description.getText().toString().trim());
                        //inserting values to DB
                        dbRef.push().setValue(ctg);
                        //toast message to user
                        Toast.makeText(getApplicationContext(), "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact No.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
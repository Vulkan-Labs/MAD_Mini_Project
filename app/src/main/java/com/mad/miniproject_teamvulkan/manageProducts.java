package com.mad.miniproject_teamvulkan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manageProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);



        Button button1 = findViewById(R.id.addNewButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProd = new Intent(getApplicationContext(), addProduct.class);
                startActivity(addProd);
            }
        });





    }
}
package com.mad.miniproject_teamvulkan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class pharmacyMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_main);

        Button button1 = findViewById(R.id.manageProductsBTN);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manProd = new Intent(getApplicationContext(), manageProducts.class);
                startActivity(manProd);
            }
        });




    }
}
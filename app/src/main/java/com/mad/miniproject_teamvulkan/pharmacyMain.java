package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Map;


public class pharmacyMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_main);

        Query readRef =  FirebaseDatabase.getInstance().getReference().child("Product");

        readRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                  //  Product product = dataSnapshot.getValue(Product.class);
                   // products.add(product);
                    int quantity =  Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());

                    total = total + quantity;


                }

                TextView inventory = findViewById(R.id.inventoryTextView);

                inventory.setText(Integer.toString(total));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





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
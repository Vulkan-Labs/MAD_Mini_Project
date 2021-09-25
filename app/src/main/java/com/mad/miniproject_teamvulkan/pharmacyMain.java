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
                int totalInventory = 0;
                float totalInventoryNet = 0;

                float expectedSales = 0;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                  //  Product product = dataSnapshot.getValue(Product.class);
                   // products.add(product);
                    int inventory =  Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                    totalInventory = totalInventory + inventory;

                    float singleInventory = Float.parseFloat(dataSnapshot.child("bprice").getValue().toString());
                    totalInventoryNet = totalInventoryNet + singleInventory;

                    float eSales = Float.parseFloat(dataSnapshot.child("price").getValue().toString());
                    expectedSales = expectedSales + eSales;

                }

                TextView inventory = findViewById(R.id.inventoryTextView);
                inventory.setText(Integer.toString(totalInventory));

                TextView inventoryNet = findViewById(R.id.inventoryNetNumberView);
                inventoryNet.setText(Float.toString(totalInventoryNet));

                TextView expectedSaleT = findViewById(R.id.expectedSalesTextNum);
                expectedSaleT.setText(Float.toString(expectedSales));

                float profit = expectedSales - totalInventoryNet;
                TextView netProfit = findViewById(R.id.expectedNetProfitTextNum);
                netProfit.setText(Float.toString(profit));



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
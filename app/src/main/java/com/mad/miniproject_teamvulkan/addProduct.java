package com.mad.miniproject_teamvulkan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addProduct extends AppCompatActivity {
    EditText  ADDProductNameInput, ADDProductPriceInput, ADDProductQuantityInput;
    Button addProductSaveButton;
    DatabaseReference dbRef;
    Product prd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ADDProductNameInput = findViewById(R.id.ADDProductNameInput);
        ADDProductPriceInput = findViewById(R.id.ADDProductPriceInput);
        ADDProductQuantityInput = findViewById(R.id.ADDProductQuantityInput);

        addProductSaveButton = findViewById(R.id.addProductSaveButton);

        DatabaseReference dbRef;

        prd = new Product();

       dbRef = FirebaseDatabase.getInstance().getReference().child("Product");
       // List<QueryDocumentSnapshot> documents = future.get().getDocuments



        Query readRef =  FirebaseDatabase.getInstance().getReference().child("Product").orderByKey().limitToLast(1);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot childDataSnapshot : datasnapshot.getChildren()) {
                    if (childDataSnapshot.child("proid").getValue() != null) {
                        ArrayList<String> products = new ArrayList<>();
                        for (DataSnapshot ing : childDataSnapshot.child("proid").getChildren()) {
                            products.add(ing.child("proid").getValue(String.class));
                        }
                        System.out.println("Gained data: " + products.toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button button1 = findViewById(R.id.addProductSaveButton);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                try{
                    if(TextUtils.isEmpty(ADDProductNameInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Product Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(ADDProductPriceInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please add a Price", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(ADDProductQuantityInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Price", Toast.LENGTH_SHORT).show();
                    else{


                        prd.setProductName(ADDProductNameInput.getText().toString().trim());
                        prd.setPrice(Float.parseFloat(ADDProductPriceInput.getText().toString().trim()));
                        prd.setQuantity(Integer.parseInt(ADDProductQuantityInput.getText().toString().trim()));

                      //  dbRef.child().setValue(prd);
                        dbRef.push().setValue(prd);

                        Toast.makeText(getApplicationContext(), "Product Added Successfully ", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                }catch (NumberFormatException e){

                    Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void clearControls(){

        ADDProductNameInput.setText("");
        ADDProductPriceInput.setText("");
        ADDProductQuantityInput.setText("");
    }

}
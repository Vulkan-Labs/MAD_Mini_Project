package com.mad.miniproject_teamvulkan;

import static com.mad.miniproject_teamvulkan.pharmacyAdaptor.EXTRA_MESSAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class editProduct extends AppCompatActivity {
    EditText ProductName, bprice, price, quantity, desc;
    Product prd;
    Button EDITProductSaveButton;
    DatabaseReference dbEditRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ProductName = findViewById(R.id.EDITProductNameInput);
        bprice = findViewById(R.id.EDITProductBuyingPriceInput);
        price = findViewById(R.id.EDITProductPriceInput);
        quantity = findViewById(R.id.EDITProductQuantityInput);
        desc = findViewById(R.id.EDITProductDescriptionInput);

        EDITProductSaveButton = findViewById(R.id.EDITProductSaveButton);

        Intent intent = getIntent();
        String prid = intent.getStringExtra(EXTRA_MESSAGE);

        prd = new Product();

        TextView txtMessage = findViewById(R.id.EDITProductIDTxt);

        txtMessage.setText(prid);

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Product").child(prid);

        //------------------------------Fetch and display data of specific product--------------------------------------//

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){

                ProductName.setText(snapshot.child("productName").getValue().toString());
                bprice.setText(snapshot.child("bprice").getValue().toString());
                price.setText(snapshot.child("price").getValue().toString());
                quantity.setText(snapshot.child("quantity").getValue().toString());
                desc.setText(snapshot.child("description").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //------------------------------Save edited data to specific product-----------------------------------------//

        dbEditRef = FirebaseDatabase.getInstance().getReference().child("Product");

        Button editProductSaveBtn = findViewById(R.id.EDITProductSaveButton);
        editProductSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dbEditRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(prid)){
                            try{
                                if(TextUtils.isEmpty(ProductName.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Product Name cannot be empty", Toast.LENGTH_SHORT).show();
                                else if(TextUtils.isEmpty(bprice.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Product Buying Price cannot be empty", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(price.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Price cannot be empty", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(quantity.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Quantity cannot be empty", Toast.LENGTH_SHORT).show();
                                else{

                                    prd.setPROID(prid);
                                    prd.setProductName(ProductName.getText().toString().trim());
                                    prd.setBprice(Float.parseFloat( bprice.getText().toString().trim()));
                                    prd.setPrice(Float.parseFloat(price.getText().toString().trim()));
                                    prd.setQuantity(Integer.parseInt(quantity.getText().toString().trim()));
                                    prd.setDescription(desc.getText().toString());

                                    //--------------------------Calculations for Quantity and Sales per product------------//
                                    prd.setInventoryNet(prd.getBprice() * prd.getQuantity());
                                    prd.setExptSales(prd.getPrice() * prd.getQuantity());

                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(prid);
                                    dbRef.setValue(prd);

                                    Toast.makeText(getApplicationContext(), "Product Eddited Successfully ", Toast.LENGTH_SHORT).show();

                                    Intent manProd = new Intent(getApplicationContext(), manageProducts.class);
                                    finish();
                                    startActivity(manProd);

                                }



                            }catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
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
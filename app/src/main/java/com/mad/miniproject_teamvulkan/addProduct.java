package com.mad.miniproject_teamvulkan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Queue;

public class addProduct extends AppCompatActivity {
    EditText ADDProductNameInput, ADDProductBuyingPriceInput, ADDProductPriceInput, ADDProductQuantityInput, ADDProductDescriptionInput;

    Button addProductSaveButton;

    Product prd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ADDProductNameInput = findViewById(R.id.ADDProductNameInput);
        ADDProductBuyingPriceInput = findViewById(R.id.ADDProductBuyingPriceInput);
        ADDProductPriceInput = findViewById(R.id.ADDProductPriceInput);
        ADDProductQuantityInput = findViewById(R.id.ADDProductQuantityInput);
        ADDProductDescriptionInput = findViewById(R.id.ADDProductDescriptionInput);


        addProductSaveButton = findViewById(R.id.addProductSaveButton);

        DatabaseReference dbRef;

        prd = new Product();

       dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

//----------------------------------------------ID Generator-----------------------------------------------------//

        Query readRef =  FirebaseDatabase.getInstance().getReference().child("Product").limitToLast(1);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.hasChildren()) {

                    for(DataSnapshot ds: datasnapshot.getChildren()){

                   String ID = ds.child("proid").getValue().toString();

                        if(ID == ""){

                            ID = "PR000000";

                        }

                   String IDnum =  ID.substring(2,8);

                   int IDconvert = Integer.valueOf(IDnum);
                   int nextID = IDconvert + 1;

                   String nextIDPrefix = "PR";

                   String nextIDSuffix = ("000000" + nextID);

                   String nextIDSuffixTrimmed = nextIDSuffix.substring(nextIDSuffix.length() - 6);


                        String finalID = nextIDPrefix + nextIDSuffixTrimmed;

                        TextView txtMessage = findViewById(R.id.viewProductID);

                       // txtMessage.setText(Integer.toString(nextID));

                       txtMessage.setText("PRODUCT ID " + finalID);

                       prd.setPROID(finalID);

                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Database Empty ID Initilised", Toast.LENGTH_SHORT).show();

                    String ID = "PR000000";

                    String IDnum =  ID.substring(2,8);

                    int IDconvert = Integer.valueOf(IDnum);
                    int nextID = IDconvert + 1;

                    String nextIDPrefix = "PR";

                    String nextIDSuffix = ("000000" + nextID);

                    String nextIDSuffixTrimmed = nextIDSuffix.substring(nextIDSuffix.length() - 6);


                    String finalID = nextIDPrefix + nextIDSuffixTrimmed;

                    TextView txtMessage = findViewById(R.id.viewProductID);

                    // txtMessage.setText(Integer.toString(nextID));

                    txtMessage.setText("PRODUCT ID " + finalID);

                    prd.setPROID(finalID);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       //-----------------------------------------Save Product to database--------------------------------------------------------//

        Button button1 = findViewById(R.id.addProductSaveButton);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try{
                    if(TextUtils.isEmpty(ADDProductNameInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Product Name", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(ADDProductBuyingPriceInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Product Buying Price cannot be blank", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(ADDProductPriceInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please add a Price", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(ADDProductQuantityInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Quantity", Toast.LENGTH_SHORT).show();
                     else{


                        prd.setProductName(ADDProductNameInput.getText().toString().trim());
                        prd.setBprice(Float.parseFloat( ADDProductBuyingPriceInput.getText().toString().trim()));
                        prd.setPrice(Float.parseFloat(ADDProductPriceInput.getText().toString().trim()));
                        prd.setQuantity(Integer.parseInt(ADDProductQuantityInput.getText().toString().trim()));
                        prd.setDescription(ADDProductDescriptionInput.getText().toString());

                       dbRef.child(prd.getPROID()).setValue(prd);

                        Toast.makeText(getApplicationContext(), "Product Added Successfully ", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                }catch (NumberFormatException e){

                    Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();

                }

                Intent manProd = new Intent(getApplicationContext(), manageProducts.class);
                manProd.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(manProd);
                finish();

            }
        });

    }

    private void clearControls(){
        ADDProductNameInput.setText("");
        ADDProductPriceInput.setText("");
        ADDProductQuantityInput.setText("");
    }

}
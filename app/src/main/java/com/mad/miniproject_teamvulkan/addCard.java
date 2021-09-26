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

public class addCard extends AppCompatActivity {

    EditText cardholderNameInput, cardNumberInput, expDateInput, cvvInput, billingAddressInput, nicknameInput;
    Button btnAddCardConfirm;
    DatabaseReference dbRef;
    payment pay;

    private void clearControls() {
        cardholderNameInput.setText("");
        cardNumberInput.setText("");
        expDateInput.setText("");
        cvvInput.setText("");
        billingAddressInput.setText("");
        nicknameInput.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_add);

        cardholderNameInput = findViewById(R.id.cardholderNameInput);
        cardNumberInput = findViewById(R.id.cardNumberInput);
        expDateInput = findViewById(R.id.expDateInput);
        cvvInput = findViewById(R.id.cvvInput);
        billingAddressInput = findViewById(R.id.billingAddressInput);
        nicknameInput = findViewById(R.id.nicknameInput);

        btnAddCardConfirm = findViewById(R.id.btnAddCardConfirm);

        pay = new payment();

        dbRef = FirebaseDatabase.getInstance().getReference().child("payment");

        Query readRef =  FirebaseDatabase.getInstance().getReference().child("payment").limitToLast(1);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.hasChildren()) {

                    for(DataSnapshot ds: datasnapshot.getChildren()){

                        String ID = ds.child("cardId").getValue().toString();

                        if(ID == ""){

                            ID = "CC000000";

                        }

                        String IDnum =  ID.substring(2,8);

                        int IDconvert = Integer.valueOf(IDnum);
                        int nextID = IDconvert + 1;

                        String nextIDPrefix = "CC";

                        String nextIDSuffix = ("000000" + nextID);

                        String nextIDSuffixTrimmed = nextIDSuffix.substring(nextIDSuffix.length() - 6);


                        String finalID = nextIDPrefix + nextIDSuffixTrimmed;

                        TextView txtMessage = findViewById(R.id.viewCardID);

                        // txtMessage.setText(Integer.toString(nextID));

                        txtMessage.setText("PRODUCT ID " + finalID);

                        pay.setCardId(finalID);

                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Database Empty ID Initilised", Toast.LENGTH_SHORT).show();

                    String ID = "CC000000";

                    String IDnum =  ID.substring(2,8);

                    int IDconvert = Integer.valueOf(IDnum);
                    int nextID = IDconvert + 1;

                    String nextIDPrefix = "CC";

                    String nextIDSuffix = ("000000" + nextID);

                    String nextIDSuffixTrimmed = nextIDSuffix.substring(nextIDSuffix.length() - 6);


                    String finalID = nextIDPrefix + nextIDSuffixTrimmed;

                    TextView txtMessage = findViewById(R.id.viewCardID);

                    // txtMessage.setText(Integer.toString(nextID));

                    txtMessage.setText("PRODUCT ID " + finalID);

                    pay.setCardId(finalID);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button button1 = findViewById(R.id.btnAddCardConfirm);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (TextUtils.isEmpty(cardholderNameInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Cardholder Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cardNumberInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Card Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(expDateInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Card Expiry Date", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cvvInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter CVV", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(billingAddressInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Billing Address", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(nicknameInput.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Card Nickname", Toast.LENGTH_LONG).show();
                    else {


                        pay.setCardholderName(cardholderNameInput.getText().toString().trim());
                        pay.setCardNumber(Integer.parseInt(cardNumberInput.getText().toString().trim()));
                        pay.setExpDate(expDateInput.getText().toString().trim());
                        pay.setCvv(Integer.parseInt(cvvInput.getText().toString().trim()));
                        pay.setBillingAddress(billingAddressInput.getText().toString());
                        pay.setNickname(nicknameInput.getText().toString());



                        dbRef.child(pay.getCardId()).setValue(pay);


                        Toast.makeText(getApplicationContext(), "Payment Method added Successfully ", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }


                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
}

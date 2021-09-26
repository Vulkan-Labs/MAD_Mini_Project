package com.mad.miniproject_teamvulkan;

import static com.mad.miniproject_teamvulkan.manageCardInfo.EXTRA_MESSAGE;

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

public class editCard extends AppCompatActivity {
    EditText cardholderNameInput, cardNumberInput, expDateInput, cvvInput, billingAddressInput, nicknameInput;
    Button btnEditCardSave;
    DatabaseReference dbEditCardRef;
    payment pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_edit);

        cardholderNameInput = findViewById(R.id.cardholderNameInput);
        cardNumberInput = findViewById(R.id.cardNumberInput);
        expDateInput = findViewById(R.id.expDateInput);
        cvvInput = findViewById(R.id.cvvInput);
        billingAddressInput = findViewById(R.id.billingAddressInput);
        nicknameInput = findViewById(R.id.nicknameInput);

        btnEditCardSave = findViewById(R.id.btnEditCardSave);

        Intent intent = getIntent();
        String cdid = intent.getStringExtra(EXTRA_MESSAGE);

        pay = new payment();

        TextView txtMessage = findViewById(R.id.editCardID);

        txtMessage.setText(cdid);

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("payment").child(cdid);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){

                    cardholderNameInput.setText(snapshot.child("cardholderName").getValue().toString());
                    cardNumberInput.setText(snapshot.child("cardNumber").getValue().toString());
                    expDateInput.setText(snapshot.child("expDate").getValue().toString());
                    cvvInput.setText(snapshot.child("cvv").getValue().toString());
                    billingAddressInput.setText(snapshot.child("billingAddress").getValue().toString());
                    nicknameInput.setText(snapshot.child("nickname").getValue().toString());



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbEditCardRef = FirebaseDatabase.getInstance().getReference().child("payment");

        Button editProductSaveBtn = findViewById(R.id.btnEditCardSave);
        editProductSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dbEditCardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(cdid)){


                            try{
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

                                    pay.setCardId(cdid);
                                    pay.setCardholderName(cardholderNameInput.getText().toString().trim());
                                    pay.setCardNumber(cardNumberInput.getText().toString().trim());
                                    pay.setExpDate(expDateInput.getText().toString().trim());
                                    pay.setCvv(cvvInput.getText().toString().trim());
                                    pay.setBillingAddress(billingAddressInput.getText().toString());
                                    pay.setNickname(nicknameInput.getText().toString());

                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("payment").child(cdid);
                                    dbRef.setValue(pay);

                                    Toast.makeText(getApplicationContext(), "Product Edited Successfully ", Toast.LENGTH_SHORT).show();

                                }

                            }catch (NumberFormatException e){

                                Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();

                            }

                            Intent viewW = new Intent(getApplicationContext(), viewWallet.class);

                            startActivity(viewW);

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
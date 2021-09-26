package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewWallet extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    manageCardInfo manageCardInfo;
    ArrayList<payment> allCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_view);

        recyclerView = findViewById(R.id.walletRecyclerView);
        database = FirebaseDatabase.getInstance().getReference("payment");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allCards = new ArrayList<>();

        manageCardInfo = new manageCardInfo(this,allCards);
        recyclerView.setAdapter(manageCardInfo);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    payment Payment = dataSnapshot.getValue(payment.class);
                    allCards.add(Payment);

                }

                manageCardInfo.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button button1 = findViewById(R.id.btnAddPayment);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addC = new Intent(getApplicationContext(), addCard.class);

                startActivity(addC);

            }
        });


    }
}

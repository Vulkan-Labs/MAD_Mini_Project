package com.mad.miniproject_teamvulkan;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class productView extends AppCompatActivity {

    private Button move;

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<products> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        recyclerView = findViewById(R.id.userList);
        dbref= FirebaseDatabase.getInstance().getReference().child("Product");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        myAdapter = new MyAdapter(this , list );
        recyclerView.setAdapter(myAdapter);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    products prod = dataSnapshot.getValue(products.class); //need to figure out this
                    list.add(prod);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"database error",
                        Toast.LENGTH_SHORT).show();
            }
        });



//        move=findViewById(R.id.review_btn);
//        move.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(productView.this,Review_view.class);
//                startActivity(intent);
//            }
//        });

    }
}
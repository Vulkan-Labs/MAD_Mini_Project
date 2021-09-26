package com.mad.miniproject_teamvulkan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Retriev_rev extends AppCompatActivity {

    TextView viewName, viewComment;
    Button editBtn , deleteBtn , goto_prod;
    RecyclerView recyclerView;
    DatabaseReference dbref;
    Review_adapter review_adapter;
    ArrayList<reviews> list_R;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retriev_rev);
        //Buttons
        editBtn = findViewById(R.id.edit_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        goto_prod =findViewById(R.id.btn_goto_products);

        recyclerView = findViewById(R.id.Review_recycleview);
        dbref = FirebaseDatabase.getInstance().getReference().child("Review");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list_R = new ArrayList<>(); //Array list initialize

        review_adapter = new Review_adapter(this , list_R);
        recyclerView.setAdapter(review_adapter);

        //retrieving data from firebase
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    reviews rev = dataSnapshot.getValue(reviews.class);
                    list_R.add(rev);
                }
                review_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        goto_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Retriev_rev.this , productView.class);
                startActivity(intent);
            }
        });

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Retriev_rev.this , EditActivity.class);
//                startActivity(intent);
//            }
//        });

//        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Review").child("rev1");
//        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.hasChildren()) {
//                    viewName.setText(snapshot.child("name").getValue().toString());
//                    viewComment.setText(snapshot.child("comment").getValue().toString());
//                }
//                else
//                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}
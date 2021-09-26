package com.mad.miniproject_teamvulkan;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.miniproject_teamvulkan.Cart.CartModel;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class productView extends AppCompatActivity{

    private Button move;

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<products> list;
    NotificationBadge notificationBadge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        recyclerView = findViewById(R.id.userList);
        dbref= FirebaseDatabase.getInstance().getReference().child("Product");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        notificationBadge.findViewById(R.id.notification);

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
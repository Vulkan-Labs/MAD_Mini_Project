package com.mad.miniproject_teamvulkan;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<products> list;

    public MyAdapter(Context context, ArrayList<products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_cards,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        products pro = list.get(position);
        holder.name.setText(pro.getProductName());
        holder.description.setText(Integer.toString(pro.getQuantity()));
        holder.price.setText(Integer.toString(pro.getPrice()));

        holder.Reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =  pro.getProductName();
                Intent newintent = new Intent(context.getApplicationContext(), Review_view.class);
                newintent.putExtra(EXTRA_MESSAGE, name);
                ((productView)context).finish();
                context.startActivity(newintent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price;
        Button Reviewbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);

            Reviewbtn = itemView.findViewById(R.id.review_btn);
        }
    }

}

package com.mad.miniproject_teamvulkan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class pharmacyAdaptor extends RecyclerView.Adapter<pharmacyAdaptor.pharmacyViewHolder> {


    Context context;

    ArrayList<Product> list;


    public static final String EXTRA_MESSAGE = "com.mad.miniproject_teamvulkan_MESSAGE";


    public pharmacyAdaptor(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public pharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product, parent, false );

        return new pharmacyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull pharmacyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Product product = list.get(position);
        holder.productName.setText(product.getProductName());
        holder.productBuyingPrice.setText(Float.toString(product.getBprice()));
        holder.productPrice.setText(Float.toString(product.getPrice()));
        holder.productQuantity.setText(Integer.toString(product.getQuantity()));
        holder.productDescription.setText(product.getDescription());

        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String prdid = product.getPROID();
                Intent editProd = new Intent(context.getApplicationContext(), editProduct.class);
                editProd.putExtra(EXTRA_MESSAGE, prdid);
                context.startActivity(editProd);
            }

        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(product.getPROID(), position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class pharmacyViewHolder extends RecyclerView.ViewHolder{

        TextView productName, productBuyingPrice, productPrice, productQuantity, productDescription;
        public Button delete , edit;

        public pharmacyViewHolder(@NonNull View itemView) {
            super(itemView);


            delete = itemView.findViewById(R.id.deletebtn);
            edit = itemView.findViewById(R.id.editbtn);

            productName = itemView.findViewById(R.id.productNameView);

            productBuyingPrice = itemView.findViewById(R.id.productBuyingPriceView);

            productPrice = itemView.findViewById(R.id.productPriceView);

            productQuantity = itemView.findViewById(R.id.productQuantityView);

            productDescription = itemView.findViewById(R.id.productDescriptionView);


        }

    }



    private void deleteProduct(String proid,  int position) {
        FirebaseDatabase.getInstance().getReference()
                .child("Product").child(proid).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, list.size());

                            Log.d("Delete Product", "Product has been deleted");
                            Toast.makeText(context,
                                    "product has been deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Delete Product", "Product couldn't be deleted");
                            Toast.makeText(context,
                                    "Product could not be deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

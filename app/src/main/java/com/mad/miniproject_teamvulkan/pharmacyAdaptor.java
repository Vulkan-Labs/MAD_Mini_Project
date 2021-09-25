package com.mad.miniproject_teamvulkan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class pharmacyAdaptor extends RecyclerView.Adapter<pharmacyAdaptor.pharmacyViewHolder> {


    Context context;

    ArrayList<Product> list;


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
    public void onBindViewHolder(@NonNull pharmacyViewHolder holder, int position) {

        Product product = list.get(position);
        holder.productName.setText(product.getProductName());
        holder.productBuyingPrice.setText(Float.toString(product.getBprice()));
        holder.productPrice.setText(Float.toString(product.getPrice()));
        holder.productQuantity.setText(Integer.toString(product.getQuantity()));
        holder.productDescription.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class pharmacyViewHolder extends RecyclerView.ViewHolder{

        TextView productName, productBuyingPrice, productPrice, productQuantity, productDescription;


        public pharmacyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productNameView);

            productBuyingPrice = itemView.findViewById(R.id.productBuyingPriceView);

            productPrice = itemView.findViewById(R.id.productPriceView);

            productQuantity = itemView.findViewById(R.id.productQuantityView);

            productDescription = itemView.findViewById(R.id.productDescriptionView);


        }




    }


}

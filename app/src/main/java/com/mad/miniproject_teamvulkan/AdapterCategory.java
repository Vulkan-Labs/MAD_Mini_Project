package com.mad.miniproject_teamvulkan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyViewHolder> {

    Context context;

    ArrayList<Category> list;

    public AdapterCategory(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Category category = list.get(position);
        holder.categoryNo.setText(category.getCategory_name());
        holder.categoryDescription.setText(category.getCategory_description());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(category.getCategory_ID(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryNo, categoryDescription;
        Button edit, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            
            categoryNo = itemView.findViewById(R.id.txt_category_no);
            categoryDescription = itemView.findViewById(R.id.txt_category_desc);
            edit = itemView.findViewById(R.id.btn_edit_categoryList);
            delete = itemView.findViewById(R.id.btn_delete_category_list);
        }
    }

    private void deleteCategory(String categoryID, final int position){
        FirebaseDatabase.getInstance().getReference()
                .child("Category").child(categoryID).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            /*list.remove(position);
                            notifyItemRemoved(position);
                            list.clear();
                            notifyItemRangeChanged(position, list.size());*/


                            Toast.makeText(context, "Category has been Deleted!", Toast.LENGTH_SHORT).show();
                            Intent manageCategories = new Intent(context.getApplicationContext(), ManageCategoriesActivity.class);
                            ((ManageCategoriesActivity)context).finish();
                            context.startActivity(manageCategories);
                        }else{
                            Toast.makeText(context, "Category couldn't be deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
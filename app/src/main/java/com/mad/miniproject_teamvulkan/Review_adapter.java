package com.mad.miniproject_teamvulkan;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class Review_adapter extends RecyclerView.Adapter<Review_adapter.MyReviewHolder> {

    Context context;
    ArrayList<reviews> list_R;

//    public static final String EXTRA_MESSAGE = "com.mad.miniproject_teamvulkan_MESSAGE";

    public Review_adapter(Context context, ArrayList<reviews> list_R) {
        this.context = context;
        this.list_R = list_R;
    }

    @NonNull
    @Override
    public MyReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_cards,parent,false);
        return new MyReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReviewHolder holder, @SuppressLint("RecyclerView") int position) {

        reviews rev = list_R.get(position);
        holder.name.setText(rev.getProName());
        holder.comment.setText(rev.getComment());
        holder.cusName.setText(rev.getName());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String revID = rev.getRevID();
                Intent intent = new Intent(context.getApplicationContext(), EditActivity.class);
                intent.putExtra(EXTRA_MESSAGE, revID);
                ((Retriev_rev)context).finish();
                context.startActivity(intent);
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReview(rev.getRevID(), position);
            }
        });

    }

    private void deleteReview(String revID, int position) {

        FirebaseDatabase.getInstance().getReference().child("Review").child(revID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Review has been Deleted", Toast.LENGTH_SHORT).show();
                    Intent newintent = new Intent(context.getApplicationContext(),Retriev_rev.class);
                    ((Retriev_rev)context).finish();
                    context.startActivity(newintent);

                }
                else{
                    Toast.makeText(context, "Coundn't Delete Review", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_R.size();
    }

    public static class MyReviewHolder extends RecyclerView.ViewHolder{

        TextView name , comment, cusName;
        Button updateBtn , deletebtn;

        public MyReviewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.view_name);
            comment = itemView.findViewById(R.id.view_comment);
            cusName = itemView.findViewById(R.id.view_cus_name);


            updateBtn = itemView.findViewById(R.id.edit_btn);
            deletebtn = itemView.findViewById(R.id.delete_btn);
        }
    }

    

}

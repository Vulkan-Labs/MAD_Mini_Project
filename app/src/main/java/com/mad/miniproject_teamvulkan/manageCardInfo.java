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

public class manageCardInfo extends RecyclerView.Adapter<manageCardInfo.cardInfoViewHolder> {


    Context context;

    ArrayList<payment> list;


    public static final String EXTRA_MESSAGE = "com.mad.miniproject_teamvulkan_MESSAGE";


    public manageCardInfo(Context context, ArrayList<payment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public cardInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false );

        return new cardInfoViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull cardInfoViewHolder holder, @SuppressLint("RecyclerView") int position) {

        payment card_view = list.get(position);
        holder.nickname.setText(card_view.getNickname());
        holder.cardNumber.setText(Float.toString(card_view.getCardNumber()));
        holder.expDate.setText(card_view.getExpDate());


        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String crdid = card_view.getCardId();

                Intent editCard = new Intent(context.getApplicationContext(), editCard.class);
                editCard.putExtra(EXTRA_MESSAGE, crdid);

                ((viewWallet)context).finish();
                context.startActivity(editCard);



            }



        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(card_view.getCardId(), position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class cardInfoViewHolder extends RecyclerView.ViewHolder{

        TextView nickname, cardNumber, expDate;
        public Button delete , edit;

        public cardInfoViewHolder(@NonNull View itemView) {
            super(itemView);


            delete = itemView.findViewById(R.id.btnDeleteCardView);
            edit = itemView.findViewById(R.id.btnEditCardview);

            nickname = itemView.findViewById(R.id.nicknameCardView);

            cardNumber = itemView.findViewById(R.id.cardNumberView);

            expDate = itemView.findViewById(R.id.expDateView);


        }

    }



    private void deleteProduct(String cardId,  int position) {
        FirebaseDatabase.getInstance().getReference()
                .child("payment").child(cardId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {




                            Log.d("Delete Payment Method", "Payment Method has been deleted");
                            Toast.makeText(context,
                                    "product has been deleted",
                                    Toast.LENGTH_SHORT).show();

                            Intent manProd = new Intent(context.getApplicationContext(), viewWallet.class);
                            ((viewWallet)context).finish();
                            context.startActivity(manProd);



                        } else {
                            Log.d("Delete Payment Method", "Payment Method couldn't be deleted");
                            Toast.makeText(context,
                                    "Payment Method could not be deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

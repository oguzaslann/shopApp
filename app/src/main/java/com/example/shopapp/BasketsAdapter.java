package com.example.shopapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BasketsAdapter extends RecyclerView.Adapter<BasketsAdapter.ViewHolder> {

    ArrayList<Basket> basketsArrayList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;


    public BasketsAdapter(ArrayList<Basket> basketsArrayList, Context context) {
        this.basketsArrayList = basketsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BasketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.row_list_baskets, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketsAdapter.ViewHolder holder, int position) {
        holder.name.setText(basketsArrayList.get(position).getName());

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(v.getContext(), UpdateBasketActivity.class);
                updateIntent.putExtra("basketId", String.valueOf(basketsArrayList.get(position).getId()));
                v.getContext().startActivity((updateIntent));
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Silme onayı");
                alert.setMessage("Listeyi silmek istediğinize emin misiniz?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(context);
                        sqlLiteHelperBasket.DeleteBasket(basketsArrayList.get(position).getId());
                        Toast.makeText(v.getContext(), "Silme başarılı", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity((mainIntent));
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basketInfoIntent = new Intent(v.getContext(), BasketInfoActivity.class);
                basketInfoIntent.putExtra("basketId", String.valueOf(basketsArrayList.get(position).getId()));
                v.getContext().startActivity((basketInfoIntent));
            }
        });

        holder.linearLayout.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return basketsArrayList.size();
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name;
        Button updateButton, deleteButton, infoButton;
        LinearLayout linearLayout;

        public  ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            infoButton = itemView.findViewById(R.id.infoButton);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}

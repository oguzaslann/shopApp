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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    ArrayList<Product> productsArrayList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;


    public ProductsAdapter(ArrayList<Product> productsArrayList, Context context) {
        this.productsArrayList = productsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.row_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        holder.name.setText(productsArrayList.get(position).getName());
        holder.description.setText(productsArrayList.get(position).getDescription());
        holder.price.setText(productsArrayList.get(position).getPrice().toString());


        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(v.getContext(), UpdateProductActivity.class);
                updateIntent.putExtra("productId", String.valueOf(productsArrayList.get(position).getId()));
                v.getContext().startActivity((updateIntent));
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Silme onayı");
                alert.setMessage("Ürünü silmek istediğinize emin misiniz?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        SqlLiteHelperProduct sqlLiteHelperProduct = new SqlLiteHelperProduct(context);
                        sqlLiteHelperProduct.DeleteProduct(productsArrayList.get(position).getId());
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

        holder.addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addToBasketIntent = new Intent(v.getContext(), AddToBasketActivity.class);
                addToBasketIntent.putExtra("productId", String.valueOf(productsArrayList.get(position).getId()));
                v.getContext().startActivity((addToBasketIntent));
            }
        });

        holder.linearLayout.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name, description, price;
        Button updateButton, deleteButton, addToBasketButton;
        LinearLayout linearLayout;

        public  ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            addToBasketButton = itemView.findViewById(R.id.addToBasketButton);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}

package com.example.shopapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BasketInfoAdapter extends RecyclerView.Adapter<BasketInfoAdapter.ViewHolder> {

    ArrayList<BasketProduct> basketProductsArrayList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;
    int boughtCounter = 0;
    int listSize = 0;
    LocalDataManager localDataManager = new LocalDataManager();


    public BasketInfoAdapter(ArrayList<BasketProduct> basketProductsArrayList, Context context) {
        this.basketProductsArrayList = basketProductsArrayList;
        this.context = context;
        listSize = basketProductsArrayList.size();
        localDataManager.removeSharedPreference(context, "listSize");
        localDataManager.setSharedPreference(context, "listSize", String.valueOf(listSize));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.row_list_basket_info_products, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketInfoAdapter.ViewHolder holder, int position) {
        Product p = new Product();
        try {
            p = basketProductsArrayList.get(position).getBpProduct();
            holder.name.setText(p.getName());
            holder.description.setText(p.getDescription());
            holder.count.setText(String.valueOf(basketProductsArrayList.get(position).getCount()));
            if(basketProductsArrayList.get(position).getCount() > basketProductsArrayList.get(position).getBoughtCount()){
                holder.isCompleted.setChecked(false);
            }
            else{
                holder.isCompleted.setChecked(true);
                boughtCounter++;
                localDataManager.removeSharedPreference(context, "boughtCounter");
                localDataManager.setSharedPreference(context, "boughtCounter", String.valueOf(boughtCounter));
            }
        } catch (Exception ex){
            Log.i("BASKETINFOADAPTERVIEW", ex.toString());
        }

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(v.getContext(), UpdateBasketProductActivity.class);
                updateIntent.putExtra("basketProductId", String.valueOf(basketProductsArrayList.get(position).getId()));
                updateIntent.putExtra("basketId", String.valueOf(basketProductsArrayList.get(position).getBasketId()));
                updateIntent.putExtra("productId", String.valueOf(basketProductsArrayList.get(position).getProductId()));
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
                        SqlLiteHelperBasketProduct sqlLiteHelperBasketProduct = new SqlLiteHelperBasketProduct(context);
                        sqlLiteHelperBasketProduct.DeleteBasketProduct(basketProductsArrayList.get(position).getId());
                        Toast.makeText(v.getContext(), "Silme başarılı", Toast.LENGTH_SHORT).show();
                        Intent basketsIntent = new Intent(v.getContext(), BasketsActivity.class);
                        v.getContext().startActivity((basketsIntent));
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

        holder.isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                SqlLiteHelperBasketProduct sqlLiteHelperBasketProduct = new SqlLiteHelperBasketProduct(context);
                BasketProduct bp = basketProductsArrayList.get(position);
                if (isChecked){
                    bp.setBoughtCount(bp.getCount());
                } else{
                    bp.setBoughtCount(0);
                }
                sqlLiteHelperBasketProduct.UpdateBasketProduct(bp);
            }
        }
        );


        holder.linearLayout.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return basketProductsArrayList.size();
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name, description, count;
        Button updateButton, deleteButton;
        CheckBox isCompleted;
        LinearLayout linearLayout;

        public  ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            count = itemView.findViewById(R.id.count);
            isCompleted = itemView.findViewById(R.id.isCompleted);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}

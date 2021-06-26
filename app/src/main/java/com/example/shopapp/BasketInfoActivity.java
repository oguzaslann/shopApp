package com.example.shopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class BasketInfoActivity extends AppCompatActivity {
    EditText name, orderLocName, orderLoc, orderDate, note;
    TextView listSizeValue;
    Button updateBasketButton, deleteBasketButton, shareBasketButton;
    ImageView imageView;
    Basket b;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_info);

        defineVeriables();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            Intent intent = getIntent();
            String basketId = intent.getStringExtra("basketId");
            SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(this);
            b = sqlLiteHelperBasket.GetBasketWithId(Integer.parseInt(basketId));
            setInputs(b);

            SqlLiteHelperBasketProduct sqlLiteHelperBasketProduct = new SqlLiteHelperBasketProduct(this);
            ArrayList<BasketProduct> basketProducts = new ArrayList<BasketProduct>();
            basketProducts = sqlLiteHelperBasketProduct.GetBasketProductsWithJoin(basketId);
            /*
            Product p5 = new Product();
            p5 = basketProducts.get(0).getBpProduct();
            Log.i("pName", p5.getName());
             */

            BasketInfoAdapter basketInfoAdapter = new BasketInfoAdapter(basketProducts, this);
            recyclerView.setAdapter(basketInfoAdapter);
        } catch (Exception ex){
            Log.i("UPDATEBASKETLOAD!", ex.toString());
        }

        updateBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(v.getContext(), UpdateBasketActivity.class);
                updateIntent.putExtra("basketId", String.valueOf(b.getId()));
                v.getContext().startActivity((updateIntent));
            }
        });

        deleteBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Silme onayı");
                alert.setMessage("Soruyu silmek istediğinize emin misiniz?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(v.getContext());
                        sqlLiteHelperBasket.DeleteBasket(b.getId());
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

        shareBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void defineVeriables() {
        name = findViewById(R.id.name);
        orderDate = findViewById(R.id.orderDate);
        orderLocName = findViewById(R.id.orderLocName);
        orderLoc = findViewById(R.id.orderLoc);
        note = findViewById(R.id.note);
        listSizeValue = findViewById(R.id.listSizeValue);
        updateBasketButton = findViewById(R.id.updateBasketButton);
        deleteBasketButton = findViewById(R.id.deleteBasketButton);
        shareBasketButton = findViewById(R.id.shareBasketButton);
        imageView = findViewById(R.id.imageView);
        recyclerView = findViewById(R.id.recylerview3);
    }

    public void setInputs(Basket basket) throws FileNotFoundException {
        name.setText(basket.getName());
        orderDate.setText(basket.getOrderDate());
        orderLocName.setText(basket.getOrderLocName());
        orderLoc.setText(basket.getOrderLoc());
        note.setText(basket.getNote());
        if(basket.getImgPath() != null){
            final Uri imageUri = Uri.parse(basket.getImgPath());
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(selectedImage);
        }


        LocalDataManager localDataManager = new LocalDataManager();
        String listSize = localDataManager.getSharedPreference(BasketInfoActivity.this, "listSize", "");
        String boughtCounter = localDataManager.getSharedPreference(BasketInfoActivity.this, "boughtCounter", "");
        listSizeValue.setText("Tamamlanan alışveriş durumu: " + boughtCounter + "/" +listSize);

    }

}
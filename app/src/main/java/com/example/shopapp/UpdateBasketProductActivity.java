package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateBasketProductActivity extends AppCompatActivity {
    TextView name, description, price, basket;
    EditText count;
    Button senderButton;
    Basket currentBasket;
    Product currentProduct;
    BasketProduct currentBasketProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_basket_product);
        defineVeriables();

        Intent intent = getIntent();
        String basketId = intent.getStringExtra("basketId");
        SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(this);
        currentBasket = sqlLiteHelperBasket.GetBasketWithId(Integer.parseInt(basketId));

        String productId = intent.getStringExtra("productId");
        SqlLiteHelperProduct sqlLiteHelperProduct = new SqlLiteHelperProduct(this);
        currentProduct = sqlLiteHelperProduct.GetProductWithId(Integer.parseInt(productId));

        String basketProductId = intent.getStringExtra("basketProductId");
        SqlLiteHelperBasketProduct sqlLiteHelperBasketProduct = new SqlLiteHelperBasketProduct(this);
        currentBasketProduct = sqlLiteHelperBasketProduct.GetBasketProductWithId(Integer.parseInt(basketProductId));
        setInputs();


        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBasketProduct.setCount(Integer.parseInt(count.getText().toString()));
                boolean updateBasketProduct = sqlLiteHelperBasketProduct.UpdateBasketProduct(currentBasketProduct);

                if(!updateBasketProduct)
                    Toast.makeText(getApplicationContext(), "Beklenmeyen bir hata oluştu.",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Güncelleme başarılı.", Toast.LENGTH_SHORT).show();

                    Intent basketsIntent = new Intent(UpdateBasketProductActivity.this, BasketsActivity.class);
                    UpdateBasketProductActivity.this.startActivity((basketsIntent));
                }
            }
        });
    }

    private void setInputs() {
        name.setText(currentProduct.getName());
        description.setText(currentProduct.getDescription());
        price.setText(currentProduct.getPrice().toString());
        basket.setText(currentBasket.getName());
        count.setText(String.valueOf(currentBasketProduct.getCount()));
    }


    private void defineVeriables() {
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        basket = findViewById(R.id.basket);
        count = findViewById(R.id.count);
        senderButton = findViewById(R.id.senderButton);
    }
}
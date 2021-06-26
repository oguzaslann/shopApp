package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout listBaskets, addBasket, listProducts, addProduct, maps, alarm;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineVeriables();

        SqlLiteHelper sqlLiteHelper = new SqlLiteHelper(MainActivity.this);
        sqlLiteHelper.createDataBase();

        listBaskets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basketsIntent = new Intent(MainActivity.this, BasketsActivity.class);
                MainActivity.this.startActivity((basketsIntent));
            }
        });

        addBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBasketIntent = new Intent(MainActivity.this, AddBasketActivity.class);
                MainActivity.this.startActivity((addBasketIntent));
            }
        });

        listProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productsIntent = new Intent(MainActivity.this, ProductsActivity.class);
                MainActivity.this.startActivity((productsIntent));
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProductIntent = new Intent(MainActivity.this, AddProductActivity.class);
                MainActivity.this.startActivity((addProductIntent));
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity((mapsIntent));
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(MainActivity.this, AlarmActivity.class);
                MainActivity.this.startActivity((alarmIntent));
            }
        });
    }

    public void defineVeriables(){
        welcome = findViewById(R.id.welcome);
        listBaskets = findViewById(R.id.listBaskets);
        addBasket = findViewById(R.id.addBasket);
        listProducts = findViewById(R.id.listProducts);
        addProduct = findViewById(R.id.addProduct);
        maps = findViewById(R.id.maps);
        alarm = findViewById(R.id.alarm);
    }
}
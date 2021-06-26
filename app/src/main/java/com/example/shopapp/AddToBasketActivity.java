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

public class AddToBasketActivity extends AppCompatActivity {
    TextView name, description, price, bpCount;
    EditText count;
    Button senderButton;
    Spinner spinner;
    Basket currentBasket;
    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_basket);

        defineVeriables();

        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        SqlLiteHelperProduct sqlLiteHelperProduct = new SqlLiteHelperProduct(this);
        currentProduct = sqlLiteHelperProduct.GetProductWithId(Integer.parseInt(productId));
        setInputs();

        SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(this);
        ArrayList<Basket> baskets = new ArrayList<Basket>();
        baskets = sqlLiteHelperBasket.GetBaskets();
        ArrayAdapter<Basket> dataAdapter = new ArrayAdapter<Basket>(this, android.R.layout.simple_spinner_item, baskets);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasketProduct bp = new BasketProduct();
                bp.setBasketId(currentBasket.getId());
                bp.setProductId(currentProduct.getId());
                bp.setCount(Integer.parseInt(count.getText().toString()));
                bp.setBoughtCount(0);
                SqlLiteHelperBasketProduct sqlLiteHelperBasketProduct = new SqlLiteHelperBasketProduct(AddToBasketActivity.this);
                boolean addBasketProduct = sqlLiteHelperBasketProduct.AddBasketProduct(bp);

                if(!addBasketProduct)
                    Toast.makeText(getApplicationContext(), "Beklenmeyen bir hata oluştu.",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();

                    Intent productsIntent = new Intent(AddToBasketActivity.this, ProductsActivity.class);
                    AddToBasketActivity.this.startActivity((productsIntent));
                }

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Basket basket = (Basket) parent.getSelectedItem();
                currentBasket = basket;
                SqlLiteHelperBasketProduct sqlLiteHelperBasketProduct = new SqlLiteHelperBasketProduct(getApplicationContext());
                int orderCount = sqlLiteHelperBasketProduct.GetBasketProductOrderCount(basket.getId(), currentProduct.getId());
                if(orderCount > 0)
                    bpCount.setText("Bu üründen sepetinizde " + String.valueOf(orderCount) + " adet vardır.");
                else
                    bpCount.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setInputs() {
        name.setText(currentProduct.getName());
        description.setText(currentProduct.getDescription());
        price.setText(currentProduct.getPrice().toString());
    }


    private void defineVeriables() {
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        count = findViewById(R.id.count);
        senderButton = findViewById(R.id.senderButton);
        spinner = findViewById(R.id.spinner);
        bpCount = findViewById(R.id.bpCount);
    }

}
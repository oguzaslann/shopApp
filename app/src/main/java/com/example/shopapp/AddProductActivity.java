package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {
    TextView title;
    EditText name, description, price;
    Button senderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        defineVeriables();

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product p = new Product();
                p.setName(name.getText().toString().trim());
                p.setDescription(description.getText().toString().trim());
                p.setPrice((Double.parseDouble(price.getText().toString())));

                SqlLiteHelperProduct sqlLiteHelperProduct = new SqlLiteHelperProduct(AddProductActivity.this);
                boolean addProduct = sqlLiteHelperProduct.AddProduct(p);

                if(!addProduct)
                    Toast.makeText(getApplicationContext(), "Beklenmeyen bir hata oluştu.",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                    Intent productsIntent = new Intent(AddProductActivity.this, ProductsActivity.class);
                    AddProductActivity.this.startActivity((productsIntent));
                    }
            }
        });
    }


    public void defineVeriables(){
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        senderButton = findViewById(R.id.senderButton);
    }
}
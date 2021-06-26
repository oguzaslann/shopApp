package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProductActivity extends AppCompatActivity {
    TextView title;
    EditText name, description, price;
    Button senderButton;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        defineVeriables();

        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        //Toast.makeText(getApplicationContext(), productId, Toast.LENGTH_SHORT).show();

        SqlLiteHelperProduct sqlLiteHelperProduct = new SqlLiteHelperProduct(this);
        product = sqlLiteHelperProduct.GetProductWithId(Integer.parseInt(productId));
        setInputs(product);

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setName(name.getText().toString().trim());
                product.setDescription(description.getText().toString().trim());
                product.setPrice((Double.parseDouble(price.getText().toString())));
                boolean updateProduct = sqlLiteHelperProduct.UpdateProduct(product);

                if(!updateProduct)
                    Toast.makeText(getApplicationContext(), "Beklenmeyen bir hata oluştu.",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Güncelleme başarılı.", Toast.LENGTH_SHORT).show();
                    Intent productsIntent = new Intent(UpdateProductActivity.this, ProductsActivity.class);
                    UpdateProductActivity.this.startActivity((productsIntent));
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
        title.setText("Ürün Güncelleme");
    }

    public void setInputs(Product product){
        name.setText(product.getName());
        description.setText(product.getDescription());
        price.setText(product.getPrice().toString());
    }
}
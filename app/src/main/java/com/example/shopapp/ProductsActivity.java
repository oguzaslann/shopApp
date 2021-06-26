package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.recylerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            ArrayList<Product> products = new ArrayList<>();
            SqlLiteHelperProduct sqlLiteHelperProduct = new SqlLiteHelperProduct(this);

            /*
            sqlLiteHelper.dropTable(sqlLiteHelper.getWritableDatabase(), "Questions");
            sqlLiteHelper.onCreate(sqlLiteHelper.getWritableDatabase());

            ArrayList<Question> defaultQuestionsList = new ArrayList<>();
            defaultQuestionsList = Question.getDefaultQuestionsList();
            for (int i=0; i < defaultQuestionsList.size(); i++){
                sqlLiteHelper.AddQuestion(defaultQuestionsList.get(i));
            }
             */

            products = sqlLiteHelperProduct.GetProducts();

            ProductsAdapter productsAdapter = new ProductsAdapter(products, this);
            recyclerView.setAdapter(productsAdapter);
        } catch (Exception ex){
            Log.i("ProductsActivity:", ex.toString());
        }
    }
}
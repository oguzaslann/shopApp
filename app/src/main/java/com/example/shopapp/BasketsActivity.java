package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class BasketsActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baskets);

        recyclerView = findViewById(R.id.recylerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            ArrayList<Basket> baskets = new ArrayList<>();
            SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(this);

            /*
            sqlLiteHelper.dropTable(sqlLiteHelper.getWritableDatabase(), "Questions");
            sqlLiteHelper.onCreate(sqlLiteHelper.getWritableDatabase());

            ArrayList<Question> defaultQuestionsList = new ArrayList<>();
            defaultQuestionsList = Question.getDefaultQuestionsList();
            for (int i=0; i < defaultQuestionsList.size(); i++){
                sqlLiteHelper.AddQuestion(defaultQuestionsList.get(i));
            }
             */

            baskets = sqlLiteHelperBasket.GetBaskets();

            BasketsAdapter basketsAdapter = new BasketsAdapter(baskets, this);
            recyclerView.setAdapter(basketsAdapter);
        } catch (Exception ex){
            Log.i("BASKETACTIVITY!", ex.toString());
        }
    }
}
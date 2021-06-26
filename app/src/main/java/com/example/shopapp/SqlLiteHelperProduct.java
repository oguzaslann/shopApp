package com.example.shopapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqlLiteHelperProduct extends SQLiteOpenHelper {
    private Context myContext;
    private static final int database_VERSION = 1;
    private static final String database_NAME = "ShopApp.db";
    private static final String table_PRODUCTS = "Products";

    private static final String P_COLUMS_Id = "Id";
    private static final String P_COLUMS_Name = "Name";
    private static final String P_COLUMS_Description = "Description";
    private static final String P_COLUMS_Price = "Price";


    public SqlLiteHelperProduct(@Nullable Context context) {
        super(context, database_NAME, null, database_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Product> GetProducts() {
        ArrayList<Product> products = new ArrayList<Product>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {P_COLUMS_Id, P_COLUMS_Name, P_COLUMS_Description, P_COLUMS_Price};
        Cursor cursor = db.query(table_PRODUCTS, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Product product = new Product();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setPrice(cursor.getDouble(3));
            products.add(product);
        }

        return products;
    }

    public Product GetProductWithId(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {P_COLUMS_Id, P_COLUMS_Name, P_COLUMS_Description, P_COLUMS_Price};
        Cursor cursor = db.query(table_PRODUCTS, columns, " Id = ?", new String[]{id.toString()}, null, null, null, null);

        Product product = new Product();
        if(cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setPrice(cursor.getDouble(3));
        }
        cursor.close();

        return product;
    }

    public boolean AddProduct(Product p) {
        Log.d("Add product", "AddProduct started.");
        boolean isComplete = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "INSERT INTO Products(" +
                    "\"" + P_COLUMS_Name + "\", " +
                    "\"" + P_COLUMS_Description + "\", " +
                    "\"" + P_COLUMS_Price + "\") " +
                    "VALUES(" +
                    "\"" + p.getName() + "\", " +
                    "\"" + p.getDescription() + "\", " +
                    p.getPrice() + " "
                    + ") ";
            db.execSQL(sql);
            db.close();
            isComplete = true;
        } catch (Exception ex) {
            Log.d("Add product Error: ", ex.toString());
        }

        return isComplete;
    }

    public boolean UpdateProduct(Product question){
        Log.d("UPDATE", "UpdateProduct started.");
        boolean isComplete = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(P_COLUMS_Name, question.getName());
            cv.put(P_COLUMS_Description, question.getDescription());
            cv.put(P_COLUMS_Price, question.getPrice());
            String[] whereArgs = new String[] {String.valueOf(question.getId())};
            db.update(table_PRODUCTS, cv, " id =? ", whereArgs);
            db.close();
            isComplete = true;
        } catch (Exception ex){
            Log.d("UPDATE", ex.toString());
        }

        return isComplete;
    }

    public void DeleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Products WHERE Id = " + id);
    }
}

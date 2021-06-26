package com.example.shopapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqlLiteHelperBasketProduct extends SQLiteOpenHelper {
    private Context myContext;
    private static final int database_VERSION = 1;
    private static final String database_NAME = "ShopApp.db";
    private static final String table_BASKET_PRODUCT = "BasketProduct";

    private static final String BP_COLUMS_Id = "Id";
    private static final String BP_COLUMS_Basket_Id = "BasketId";
    private static final String BP_COLUMS_Product_Id = "ProductId";
    private static final String BP_COLUMS_Count = "Count";
    private static final String BP_COLUMS_Bought_Count = "BoughtCount";


    public SqlLiteHelperBasketProduct(@Nullable Context context) {
        super(context, database_NAME, null, database_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<BasketProduct> GetBasketProducts() {
        ArrayList<BasketProduct> basketProducts = new ArrayList<BasketProduct>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {BP_COLUMS_Id, BP_COLUMS_Basket_Id, BP_COLUMS_Product_Id, BP_COLUMS_Count, BP_COLUMS_Bought_Count};
        Cursor cursor = db.query(table_BASKET_PRODUCT, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BasketProduct basketProduct = new BasketProduct();
            basketProduct.setId(cursor.getInt(0));
            basketProduct.setBasketId(cursor.getInt(1));
            basketProduct.setProductId(cursor.getInt(2));
            basketProduct.setCount(cursor.getInt(3));
            basketProduct.setBoughtCount(cursor.getInt(4));
            basketProducts.add(basketProduct);
        }

        return basketProducts;
    }

    public ArrayList<BasketProduct> GetBasketProductsWithJoin(String basketId) {
        ArrayList<BasketProduct> basketProducts = new ArrayList<BasketProduct>();

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {"bp.Id", "bp.BasketId", "bp.ProductId", "bp.Count", "bp.BoughtCount", "p.Name", "p.Description", "p.Price"};
            String[] columns2 = {"Id", "BasketId", "ProductId", "Count", "BoughtCount", "Name", "Description", "Price"};
            //Cursor cursor = db.query(table_BASKET_PRODUCT, columns, null, null, null, null, null, null);

            String table2 =  "BasketProduct bp \n" +
                    "INNER JOIN \n" +
                    "Baskets b \n" +
                    "ON \n" +
                    "bp.BasketId = b.Id \n" +
                    "INNER JOIN \n" +
                    "Products p \n" +
                    "ON \n" +
                    "bp.ProductId = p.Id ";

            String joinQuery = "SELECT bp.Id, bp.BasketId, bp.ProductId, bp.Count, bp.BoughtCount, p.Name, p.Description, p.Price \n" +
                    "FROM BasketProduct bp \n" +
                    "INNER JOIN \n" +
                    "Baskets b \n" +
                    "ON \n" +
                    "bp.BasketId = b.Id \n" +
                    "INNER JOIN \n" +
                    "Products p \n" +
                    "ON \n" +
                    "bp.ProductId = p.Id \n" +
                    "WHERE bp.BasketId = " + basketId;

            //Cursor cursor = db.query(table2, columns2, null, null, null, null, null, null);
            Cursor cursor = db.rawQuery(joinQuery, null);
            while (cursor.moveToNext()) {
                Product p2 = new Product();
                BasketProduct basketProduct = new BasketProduct();
                basketProduct.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                basketProduct.setBasketId(cursor.getInt(cursor.getColumnIndex("BasketId")));
                basketProduct.setProductId(cursor.getInt(cursor.getColumnIndex("ProductId")));
                basketProduct.setCount(cursor.getInt(cursor.getColumnIndex("Count")));
                basketProduct.setBoughtCount(cursor.getInt(cursor.getColumnIndex("BoughtCount")));
                p2.setName(cursor.getString(cursor.getColumnIndex("Name")));
                p2.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                p2.setPrice(cursor.getDouble(cursor.getColumnIndex("Price")));
                basketProduct.setBpProduct(p2);


                basketProducts.add(basketProduct);
            }
        } catch (Exception ex){
            Log.i("BASKETPRODUCTJOIN! ", ex.toString());
        }

        return basketProducts;
    }

    public BasketProduct GetBasketProductWithId(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {BP_COLUMS_Id, BP_COLUMS_Basket_Id, BP_COLUMS_Product_Id, BP_COLUMS_Count, BP_COLUMS_Bought_Count};
        Cursor cursor = db.query(table_BASKET_PRODUCT, columns, " Id = ?", new String[]{id.toString()}, null, null, null, null);

        BasketProduct basketProduct = new BasketProduct();
        if(cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            basketProduct.setId(cursor.getInt(0));
            basketProduct.setBasketId(cursor.getInt(1));
            basketProduct.setProductId(cursor.getInt(2));
            basketProduct.setCount(cursor.getInt(3));
            basketProduct.setBoughtCount(cursor.getInt(4));
        }
        cursor.close();

        return basketProduct;
    }

    public int GetBasketProductOrderCount(int basketId, int productId){
        int count = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {BP_COLUMS_Id, BP_COLUMS_Basket_Id, BP_COLUMS_Product_Id, BP_COLUMS_Count, BP_COLUMS_Bought_Count};
        Cursor cursor = db.query(table_BASKET_PRODUCT, columns, " basketId= ? AND productId= ? ", new String[]{String.valueOf(basketId), String.valueOf(productId)}, null, null, null, null);
        while (cursor.moveToNext()) {
            count = cursor.getInt(3);
        }

        return count;
    }

    public boolean AddBasketProduct(BasketProduct bp) {
        Log.d("AddBasketProduct", "AddBasketProduct started.");
        boolean isComplete = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(BP_COLUMS_Basket_Id, bp.getBasketId());
            cv.put(BP_COLUMS_Product_Id, bp.getProductId());
            cv.put(BP_COLUMS_Count, bp.getCount());
            cv.put(BP_COLUMS_Bought_Count, bp.getBoughtCount());
            db.insert(table_BASKET_PRODUCT, null, cv);
            db.close();
            isComplete = true;
        } catch (Exception ex) {
            Log.d("AddBasketProductError: ", ex.toString());
        }

        return isComplete;
    }

    public boolean UpdateBasketProduct(BasketProduct bp){
        Log.d("UPDATE", "UpdateBasketProduct started.");
        boolean isComplete = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(BP_COLUMS_Basket_Id, bp.getBasketId());
            cv.put(BP_COLUMS_Product_Id, bp.getProductId());
            cv.put(BP_COLUMS_Count, bp.getCount());
            cv.put(BP_COLUMS_Bought_Count, bp.getBoughtCount());
            String[] whereArgs = new String[] {String.valueOf(bp.getId())};
            db.update(table_BASKET_PRODUCT, cv, " id =? ", whereArgs);
            db.close();
            isComplete = true;
        } catch (Exception ex){
            Log.d("UPDATE", ex.toString());
        }

        return isComplete;
    }

    public void DeleteBasketProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from BasketProduct WHERE Id = " + id);
    }
}

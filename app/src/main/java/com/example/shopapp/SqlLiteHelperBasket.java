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

public class SqlLiteHelperBasket extends SQLiteOpenHelper {
    private Context myContext;
    private static final int database_VERSION = 1;
    private static final String database_NAME = "ShopApp.db";
    private static final String table_BASKETS = "Baskets";

    private static final String B_COLUMS_Id = "Id";
    private static final String B_COLUMS_Name = "Name";
    private static final String B_COLUMS_Order_Date = "OrderDate";
    private static final String B_COLUMS_Order_Loc_Name = "OrderLocName";
    private static final String B_COLUMS_Order_Loc = "OrderLoc";
    private static final String B_COLUMS_Img_Path = "ImgPath";
    private static final String B_COLUMS_Note = "Note";


    public SqlLiteHelperBasket(@Nullable Context context) {
        super(context, database_NAME, null, database_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Basket> GetBaskets() {
        ArrayList<Basket> baskets = new ArrayList<Basket>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = { B_COLUMS_Id, B_COLUMS_Name, B_COLUMS_Order_Date, B_COLUMS_Order_Loc_Name, B_COLUMS_Order_Loc, B_COLUMS_Img_Path, B_COLUMS_Note };
        Cursor cursor = db.query(table_BASKETS, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Basket basket = new Basket();
            basket.setId(cursor.getInt(0));
            basket.setName(cursor.getString(1));
            basket.setOrderDate(cursor.getString(2));
            basket.setOrderLocName(cursor.getString(3));
            basket.setOrderLoc(cursor.getString(4));
            basket.setImgPath(cursor.getString(5));
            basket.setNote(cursor.getString(6));
            baskets.add(basket);
        }

        return baskets;
    }

    public Basket GetBasketWithId(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = { B_COLUMS_Id, B_COLUMS_Name, B_COLUMS_Order_Date, B_COLUMS_Order_Loc_Name, B_COLUMS_Order_Loc, B_COLUMS_Img_Path, B_COLUMS_Note };
        Cursor cursor = db.query(table_BASKETS, columns, " Id = ?", new String[]{id.toString()}, null, null, null, null);

        Basket basket = new Basket();
        if(cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            basket.setId(cursor.getInt(0));
            basket.setName(cursor.getString(1));
            basket.setOrderDate(cursor.getString(2));
            basket.setOrderLocName(cursor.getString(3));
            basket.setOrderLoc(cursor.getString(4));
            basket.setImgPath(cursor.getString(5));
            basket.setNote(cursor.getString(6));
        }
        cursor.close();

        return basket;
    }

    public boolean AddBasket(Basket b) {
        Log.d("AddBasket", "AddBasket started.");
        boolean isComplete = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(B_COLUMS_Name, b.getName());
            cv.put(B_COLUMS_Order_Date, b.getOrderDate());
            cv.put(B_COLUMS_Order_Loc_Name, b.getOrderLocName());
            cv.put(B_COLUMS_Order_Loc, b.getOrderLoc());
            cv.put(B_COLUMS_Img_Path, b.getImgPath());
            cv.put(B_COLUMS_Note, b.getNote());
            db.insert(table_BASKETS, null, cv);
            db.close();
            isComplete = true;
        } catch (Exception ex) {
            Log.d("Add basket Error: ", ex.toString());
        }

        return isComplete;
    }

    public boolean UpdateBasket(Basket b){
        Log.d("UPDATE", "UpdateBasket started.");
        boolean isComplete = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(B_COLUMS_Name, b.getName());
            cv.put(B_COLUMS_Order_Date, b.getOrderDate());
            cv.put(B_COLUMS_Order_Loc_Name, b.getOrderLocName());
            cv.put(B_COLUMS_Order_Loc, b.getOrderLoc());
            cv.put(B_COLUMS_Img_Path, b.getImgPath());
            cv.put(B_COLUMS_Note, b.getNote());
            String[] whereArgs = new String[] {String.valueOf(b.getId())};
            db.update(table_BASKETS, cv, " id =? ", whereArgs);
            db.close();
            isComplete = true;
        } catch (Exception ex){
            Log.d("UPDATE", ex.toString());
        }

        return isComplete;
    }

    public void DeleteBasket(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Baskets WHERE Id = " + id);
    }
}

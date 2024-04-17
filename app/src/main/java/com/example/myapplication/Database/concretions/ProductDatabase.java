package com.example.myapplication.Database.concretions;

import android.content.ContentValues;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.abstraction.IProductDatabase;
import com.example.myapplication.Model.Product;
import com.example.myapplication.config.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDatabase implements IProductDatabase {
    private final  DatabaseHelper databaseHelper ;

    public ProductDatabase(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public long createProduct(Product product) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.insert(Constants.TABLE_PRODUCT, null, mapperContentValueFromProduct(product));
        db.close();
        return result;
    }

    @Override
    public int updateProduct(Product product) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.update(Constants.TABLE_PRODUCT, mapperContentValueFromProduct(product), "id = ?", new String[]{String.valueOf(product.getId())});
        db.close();
        return result;
    }

    @Override
    public Product readProductById(int id) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_PRODUCT, null, "id =?",new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Product product = mapperProductFromCusor(cursor);
        cursor.close();
        return product;
    }

    @Override
    public List<Product> readAllProduct() {

        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_PRODUCT;
            cursor = db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()){
                do{
                    Product product = mapperProductFromCusor(cursor);
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } catch (RuntimeException e) {
            Log.e("Tag", Objects.requireNonNull(e.getMessage()));
        } catch (Exception sqLiteException) {
            Log.e("Tag", sqLiteException.getMessage());
        }
        cursor.close();
         db.close();
        return products;
    }


    @Override
    public int deleteProductById(int id) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete(Constants.TABLE_PRODUCT, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
    private ContentValues mapperContentValueFromProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("brand", product.getBrand());
        values.put("vol", product.getVol());
        if(product.getImgUrl() != null){
            values.put("img_url", product.getImgUrl());
        }
        values.put("capital_price", product.getCapitalPrice());
        values.put("price", product.getPrice());
        values.put("amount" ,product.getAmount());
        return values;
    }
    private Product mapperProductFromCusor(Cursor cursor){
        Product product = new Product();
        product.setId(Integer.parseInt(cursor.getString(0)));
        product.setName(cursor.getString(1));
        product.setBrand(cursor.getString(2));
        product.setVol(Integer.parseInt(cursor.getString(3)));
        product.setImgUrl(cursor.getString(4));
        product.setCapitalPrice(Integer.parseInt(cursor.getString(5)));
        product.setPrice(Integer.parseInt(cursor.getString(6)));
        product.setAmount(Integer.parseInt(cursor.getString(7)));
        return product;
    }
}

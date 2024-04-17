package com.example.myapplication.Database.concretions;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.abstraction.IOrderDatabase;
import com.example.myapplication.Model.Order;
import com.example.myapplication.Model.Product;
import com.example.myapplication.config.Constants;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDatabase implements IOrderDatabase  {
    private final DatabaseHelper databaseHelper;


    public OrderDatabase(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    @Override
    public long createOrder(Order order) {
        SQLiteDatabase db  = databaseHelper.getWritableDatabase();
        ProductDatabase productDatabase = new ProductDatabase(databaseHelper);
        Product product = productDatabase.readProductById(order.getIdProduct());
        if(product == null){
            db.close();
            return -1;
        }
        else if (product.getAmount() < order.getAmount()){
            db.close();
            return -2;
        }
        else{
            int amountNew = product.getAmount() - order.getAmount();
            product.setAmount(amountNew);
            int resultUpdate = updateProduct(product);
            if(resultUpdate < 0){
                return resultUpdate;
            }
            long result = db.insert(Constants.TABLE_ORDER, null, mapperContentValueFromOrder(order));
            db.close();
            return result;
        }
    }

    @Override
    public int updateOrder(Order order) {
        Order order1 = readOrderById(order.getId());
        SQLiteDatabase db  = databaseHelper.getWritableDatabase();
        int amountPrev = order1.getAmount();
        if(amountPrev > order.getAmount()){
            int delta = amountPrev - order.getAmount();
            ProductDatabase productDatabase = new ProductDatabase(databaseHelper);
            Product product = productDatabase.readProductById(order.getIdProduct());
            if(product == null){
                db.close();
                return -1;
            }
            else if (product.getAmount() < order.getAmount()){
                db.close();
                return -2;
            }
            else{
                product.setAmount(product.getAmount() + delta);
                int resultUpdate = updateProduct(product);
                if(resultUpdate < 0){
                    return resultUpdate;
                }
            }
            int result = db.update(Constants.TABLE_ORDER, mapperContentValueFromOrder(order), "id = ?", new String[]{String.valueOf(order.getId())});
            db.close();
            return result;
        }
        else if(amountPrev < order.getAmount()){
            int delta = order.getAmount() - amountPrev ;
            int amountNew = delta + amountPrev;
            ProductDatabase productDatabase = new ProductDatabase(databaseHelper);
            Product product = productDatabase.readProductById(order.getIdProduct());
            if(product == null){
                db.close();
                return -1;
            }
            else if (product.getAmount() < amountNew){
                db.close();
                return -2;
            }
            else{
                product.setAmount(product.getAmount() - delta);
                int resultUpdate = updateProduct(product);
                if(resultUpdate < 0){
                    return resultUpdate;
                }
            }
            int result = db.update(Constants.TABLE_ORDER, mapperContentValueFromOrder(order), "id = ?", new String[]{String.valueOf(order.getId())});
            db.close();
            return result;
        }
        else{
            int result = db.update(Constants.TABLE_ORDER, mapperContentValueFromOrder(order), "id = ?", new String[]{String.valueOf(order.getId())});
            db.close();
            return result;
        }
    }

    @Override
    public Order readOrderById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_ORDER, null, "id =?",new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Order order = mapperOrderFromCursor(cursor);
        cursor.close();
        return order;
    }

    @Override
    public List<Order> readAllOrder() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor  = null;
        try{
            String selectQuery = "SELECT * from " + Constants.TABLE_ORDER;
            cursor = db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()){
                do{
                    Order order = mapperOrderFromCursor(cursor);
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (RuntimeException e) {
            Log.e("Tag", Objects.requireNonNull(e.getMessage()));
        } catch (Exception sqLiteException) {
            Log.e("Tag", sqLiteException.getMessage());
        }
        cursor.close();
         db.close();
        return orders;
    }

    @Override
    public int deleteOrderById(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Order order = readOrderById(id);
        ProductDatabase productDatabase = new ProductDatabase(databaseHelper);
        Product product = productDatabase.readProductById(order.getIdProduct());
        if(product == null){
            db.close();
            return -1;
        }
        else {
            product.setAmount(product.getAmount() + order.getAmount());
            int resultUpdate = updateProduct(product);
            if(resultUpdate < 0){
                return resultUpdate;
            }
            int result = db.delete(Constants.TABLE_ORDER, "id = ?", new String[]{String.valueOf(id)});
            db.close();
            return result;
        }

    }
    private ContentValues mapperContentValueFromOrder(Order order){
        ContentValues values = new ContentValues();
        values.put("id_product", order.getIdProduct());
        values.put("name_customer", order.getNameCustomer());
        values.put("phone_customer", order.getPhoneCustomer());
        values.put("address_customer", order.getAddressCustomer());
        values.put("status", order.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeOrderString = dateFormat.format(order.getTimeOrder());
        values.put("time_order", timeOrderString);
        values.put("amount", order.getAmount());
        return values;
    }
    private Order mapperOrderFromCursor(Cursor cursor){
        Order order = new Order();
        order.setId(Integer.parseInt(cursor.getString(0)));
        order.setIdProduct(Integer.parseInt(cursor.getString(1)));
        order.setNameCustomer(cursor.getString(2));
        order.setPhoneCustomer(cursor.getString(3));
        order.setAddressCustomer(cursor.getString(4));
        order.setStatus(cursor.getString(5));
        order.setTimeOrder(Timestamp.valueOf(cursor.getString(6)));
        order.setAmount(Integer.parseInt(cursor.getString(7)));
        return order;
    }

    public int updateProduct(Product product) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.update(Constants.TABLE_PRODUCT, mapperContentValueFromProduct(product), "id = ?", new String[]{String.valueOf(product.getId())});
//        db.close();
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
}

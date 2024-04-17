package com.example.myapplication.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderTable {

    private final  DatabaseHelper databaseHelper ;
    private static final String TABLE_ORDERS = "orders";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TOTAL = "total";

    public OrderTable(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }


    // Thêm đơn hàng mới
    public long addOrder(Order order) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, order.getName());
        values.put(KEY_TOTAL, order.getTotal());

        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return result;
    }

    // Cập nhật đơn hàng
    public int updateOrder(Order order) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, order.getName());
        values.put(KEY_TOTAL, order.getTotal());

        int result = db.update(TABLE_ORDERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(order.getId())});
        db.close();
        return result;
    }

    // Xóa đơn hàng
    public int deleteOrder(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete(TABLE_ORDERS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // Lấy thông tin đơn hàng dựa trên ID
    public Order getOrderById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, new String[]{KEY_ID,
                        KEY_NAME, KEY_TOTAL}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Order order = new Order(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)));

        cursor.close();
        db.close();
        return order;
    }

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(Integer.parseInt(cursor.getString(0)));
                order.setName(cursor.getString(1));
                order.setTotal(Double.parseDouble(cursor.getString(2)));

                orderList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orderList;
    }
}

package com.example.myapplication.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.config.Constants;
import com.example.myapplication.utils.MyApp;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GasStore.db";
    private static DatabaseHelper databaseHelper;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + Constants.TABLE_PRODUCT + " ( id integer primary key autoincrement , name varchar(100) not null, brand varchar(20) not null, vol integer not null, img_url varchar(100), capital_price integer not null, price integer not null, amount integer)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        Log.v("tag","create table product ");
        String CREARE_CUSTOMERS_TABLE = "CREATE TABLE " + Constants.TABLE_CUSTOMER + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100) not null, phone VARCHAR(10) not null, address VARCHAR(100) not null, gender VARCHAR(5), password VARCHAR(20), avatar  VARCHAR(100) )";
        db.execSQL(CREARE_CUSTOMERS_TABLE);
        String CREATE_ORDER_TABLE = "CREATE TABLE " + Constants.TABLE_ORDER + "( id INTEGER PRIMARY KEY, id_product INTEGER, name_customer VARCHAR(100) not null,  phone_customer VARCHAR(10) not null, address_customer VARCHAR(100) not null, status varchar(50), time_order TIMESTAMP not null, amount integer not null)";
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_ORDER);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }






















//    private static final String TABLE_NAME = "users";
//    private static final String COL_1 = "ID";
//    private static final String COL_2 = "USERNAME";
//    private static final String COL_3 = "PASSWORD";
//
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }

//    public boolean registerUser(String username, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2, username);
//        contentValues.put(COL_3, password);
//        long result = db.insert(TABLE_NAME, null, contentValues);
//        return result != -1;
//    }
//
//    public boolean checkUser(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME=? AND PASSWORD=?", new String[]{username, password});
//        return cursor.getCount() > 0;
//    }
}

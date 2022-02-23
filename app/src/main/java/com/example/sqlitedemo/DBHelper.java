package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = " CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_PASSWORD = "CUSTOMER_PASSWORD";
    public  static  final  String  Column_Id = "ID";
    public DBHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Create_Table = "CREATE TABLE " + CUSTOMER_TABLE + " (" + Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_PASSWORD + " TEXT)";
        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(customer_model cust)
    {
        //parent class havve this method that we are implementing
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME,cust.getUsername());
        values.put(COLUMN_CUSTOMER_PASSWORD,cust.getPassword());
        long insert = sdb.insert(CUSTOMER_TABLE, null, values);
        if(insert<0)
            return false;
        return true;

    }
    public boolean delete(customer_model customerModel)
    {
        SQLiteDatabase sdb = this.getWritableDatabase();
        Cursor cursor = sdb.rawQuery("DELETE FROM "+CUSTOMER_TABLE+" WHERE "+Column_Id+" = "+customerModel.getId(),null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }

    public List<customer_model> getEveryOne()
    {
        List<customer_model> returnList = new ArrayList<>();
        SQLiteDatabase sdb = this.getReadableDatabase();
        Cursor cursor = sdb.rawQuery("SELECT * FROM "+CUSTOMER_TABLE,null);
        if(cursor.moveToFirst())
        {
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String customerPass = cursor.getString(2);
                customer_model cm = new customer_model(customerName, customerPass, customerID);
                returnList.add(cm);
            }while(cursor.moveToNext());
        }
        else
        {
            cursor.close();
            sdb.close();
            return returnList;
        }
        return returnList;
    }

}

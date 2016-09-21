package com.example.android.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by robert on 9/21/16.
 * This class is responsible for everything done with the database.
 */
public class myDBManager extends SQLiteOpenHelper{
    // Define some global database stuff
    private static final int        DATABASE_VERSION    = 1;
    private static final String     DATABASE_NAME       = "database";

    // Define a table and some columns
    private static final String     TABLE_NAME          = "products";
    private static final String     COLUMN_ID           = "_id";
    private static final String     COLUMN_PRODUCT_NAME = "productname";

    // think about cleaning this up in terms of this constructor.
    // super already takes care of what should be passed in.
    // the constructor here could be myDBManager(Context context)
    public myDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, name, factory, version); // original super.
        super(context, DATABASE_NAME, factory , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // put together a sql string to create the table
        String query =  "CREATE TABLE "         + TABLE_NAME + "(" +
                        COLUMN_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PRODUCT_NAME     + " VARCHAR(255) " +
                ");";
        // execute the sql command
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // delete the existing database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // call onCreate
        onCreate(db);
    }

    public void addItem(Products product){
        // get reference to the database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        // Associate column name with value
        values.put(COLUMN_PRODUCT_NAME, product.get_productname());

        // insert value into table at the appropriate location
        db.insert(TABLE_NAME, null, values);
        // done writing
        db.close();
    }

    // Delete an item from the database
    public void delItem(String product_name){
        // Get database reference
        SQLiteDatabase db = getWritableDatabase();

        // build the sql query
        String query =  "DELETE FROM "  + TABLE_NAME +
                        " WHERE "       + COLUMN_PRODUCT_NAME +
                        "=\""           + product_name + "\";";
        //execute the query
        db.execSQL(query);
    }

    public String dbToString(){
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();
        String dbString = "";

        // Build a sql query
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

        // Point the cursor at the result of the executed query
        Cursor c = db.rawQuery(query, null);

        // Start at the beginning
        c.moveToFirst();

        // Iterate through the rows until the end is reached
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_ID))!=null){
                dbString += c.getString(c.getColumnIndex(COLUMN_ID));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex(COLUMN_PRODUCT_NAME));
                dbString += "\n";
            }
            // point to the next row
            c.moveToNext();
        }
        // close the database
        db.close();
        // send back the data
        return dbString;
    }
}

package com.example.passafe;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="mydatabase.db";
    private static final String TABLE_NAME="userdata";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_COUNTRY="country";
 
    SQLiteDatabase mydatabase;

    public Database_handler(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION,null);
        mydatabase=getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY,"+COLUMN_NAME+" TEXT, "+COLUMN_COUNTRY+" TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

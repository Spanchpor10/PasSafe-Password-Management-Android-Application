package com.example.passafe;

import static com.example.passafe.dashboard.USERTABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pasSafeDb";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "userdetails";

    public static final String COLUMN_ID = "id";

    public static final String PCOLUMN_SITEID = "siteid";
    public static final String PCOLUMN_SITENAME = "sitename";
    public static final String PCOLUMN_SITEUSERNAME = "siteusername";
    public static final String PCOLUMN_SITEPASSWORD = "sitepassword";
    public static final String COLUMN_USERNAME = "name";
    public static final String COLUMN_PASSWORD = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT ) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERTABLE);
        onCreate(db);
    }

    // Add a new user to userdetails table
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Get all user records (id, name, password)
    public ArrayList<UserManager> getUser() {
        ArrayList<UserManager> arrUser = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                UserManager userManager = new UserManager();
                userManager.id = cursor.getInt(0);
                userManager.UserName = cursor.getString(1);
                userManager.PassWord = cursor.getString(2);
                arrUser.add(userManager);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return arrUser;
    }

    // Get all usernames only
    public ArrayList<UserManager> getUsernames() {
        ArrayList<UserManager> arrUsernames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                UserManager userManager = new UserManager();
                userManager.UserName = cursor.getString(0);
                arrUsernames.add(userManager);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return arrUsernames;
    }

    // Get all passwords only
    public ArrayList<UserManager> getPasswords() {
        ArrayList<UserManager> arrPasswords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                UserManager userManager = new UserManager();
                userManager.PassWord = cursor.getString(0);
                arrPasswords.add(userManager);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return arrPasswords;
    }

    // Get password details from user-specific table
    public ArrayList<passwordmanager> getPasswordDetails() {
        ArrayList<passwordmanager> arrPasswordDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + USERTABLE, null);
            while (cursor.moveToNext()) {
                passwordmanager pwManager = new passwordmanager();
                pwManager.siteid = cursor.getInt(0);
                pwManager.sitename = cursor.getString(1);
                pwManager.siteusername = cursor.getString(2);
                pwManager.sitepassword = cursor.getString(3);
                arrPasswordDetails.add(pwManager);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return arrPasswordDetails;
    }

    // Insert a password record into user-specific table
    public void addPasswords(int siteid, String sitename, String siteusername, String sitepassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PCOLUMN_SITEID, siteid);
        values.put(PCOLUMN_SITENAME, sitename);
        values.put(PCOLUMN_SITEUSERNAME, siteusername);
        values.put(PCOLUMN_SITEPASSWORD, sitepassword);

        db.insert(USERTABLE, null, values);
    }

    // Update password record with parameterized query for safety
    public void updatePasswords(int oldId, int siteid, String sitename, String siteusername, String sitepassword, String userTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PCOLUMN_SITEID, siteid);
        values.put(PCOLUMN_SITENAME, sitename);
        values.put(PCOLUMN_SITEUSERNAME, siteusername);
        values.put(PCOLUMN_SITEPASSWORD, sitepassword);

        String whereClause = PCOLUMN_SITEID + "=?";
        String[] whereArgs = new String[]{String.valueOf(oldId)};
        db.update(userTable, values, whereClause, whereArgs);
    }

    // Delete password record with parameterized query for safety
    public void deletePassword(int siteId, String userTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = PCOLUMN_SITEID + "=?";
        String[] whereArgs = new String[]{String.valueOf(siteId)};
        db.delete(userTable, whereClause, whereArgs);
    }
}

package com.example.passafe;

import static com.example.passafe.dashboard.USERTABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="pasSafeDb";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="userdetails";
    public static final String PTABLE_NAME="passworddetails";

    public static final String COLUMN_ID="id";

    public static final String PCOLUMN_SITEID="siteid";
    public static final String PCOLUMN_SITENAME="sitename";
    public static final String PCOLUMN_SITEUSERNAME="siteusername";
    public static final String PCOLUMN_SITEPASSWORD="sitepassword";
    public static final String COLUMN_USERNAME="name";
    public static final String COLUMN_PASSWORD="email";
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable=" CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                            + COLUMN_USERNAME + " TEXT , " + COLUMN_PASSWORD + " TEXT ) ";
        db.execSQL(createTable);

//        String createPTable="CREATE TABLE "+ PTABLE_NAME + " ( " + PCOLUMN_SITEID + " INTEGER PRIMARY KEY  , "
//                            + PCOLUMN_SITENAME + " TEXT , " + PCOLUMN_SITEUSERNAME + " TEXT , " + PCOLUMN_SITEPASSWORD + " TEXT ) ";
//        db.execSQL(createPTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.execSQL(" DROP TABLE IF EXISTS " + USERTABLE);
        onCreate(db);
    }


    public boolean addUser(String username,String password){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USERNAME,username);
        values.put(COLUMN_PASSWORD,password);

        long result=db.insert(TABLE_NAME,null,values);
        return result!=1;
    }


    public ArrayList<UserManager> getUser(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM " + TABLE_NAME ,null);
        ArrayList<UserManager> arruser= new ArrayList<>();
        while (cursor.moveToNext()){
            UserManager userManager=new UserManager();
            userManager.id=cursor.getInt(0);
            userManager.UserName=cursor.getString(1);
            userManager.PassWord=cursor.getString(2);

            arruser.add(userManager);
        }
        return arruser;
    }

    public ArrayList<UserManager> getUsernames(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name FROM " + TABLE_NAME,null);
        ArrayList<UserManager> arruseernames=new ArrayList<>();
        while(cursor.moveToNext()){
            UserManager userManager=new UserManager();
            userManager.UserName=cursor.getString(0);
            arruseernames.add(userManager);
        }
        return arruseernames;
    }

    public ArrayList<UserManager> getPasswords(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT email FROM " + TABLE_NAME,null);
        ArrayList<UserManager> arrpasswords=new ArrayList<>();
        while(cursor.moveToNext()){
            UserManager userManager=new UserManager();
            userManager.PassWord=cursor.getString(0);
            arrpasswords.add(userManager);
        }
        return arrpasswords;
    }


    public ArrayList<passwordmanager> getPasswordDetails(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ USERTABLE ,null);
        ArrayList<passwordmanager> arrpassworddetails=new ArrayList<>();
        while(cursor.moveToNext()){
            passwordmanager passwordmanager=new passwordmanager();
            passwordmanager.siteid=cursor.getInt(0);
            passwordmanager.sitename=cursor.getString(1);
            passwordmanager.siteusername=cursor.getString(2);
            passwordmanager.sitepassword=cursor.getString(3);
            arrpassworddetails.add(passwordmanager);
        }
        return arrpassworddetails;
    }


    public void addPasswords(int siteid,String sitename,String siteusername,String sitepassword){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(PCOLUMN_SITEID,siteid);
        values.put(PCOLUMN_SITENAME,sitename);
        values.put(PCOLUMN_SITEUSERNAME,siteusername);
        values.put(PCOLUMN_SITEPASSWORD,sitepassword);

        db.insert(USERTABLE,null,values);
    }

    public void updatePasswords(int oldId,int siteid,String sitename,String siteusername,String sitepassword,String USERTABLE){
        SQLiteDatabase db=this.getWritableDatabase();
        String updateValues="UPDATE "+ USERTABLE +" SET "+ PCOLUMN_SITEID +" = "+ siteid +", "
                + PCOLUMN_SITENAME +" = '"+ sitename +"', "+ PCOLUMN_SITEUSERNAME +" = '"+ siteusername +"', "
                + PCOLUMN_SITEPASSWORD +" = '"+ sitepassword +"' WHERE "+ PCOLUMN_SITEID +" = "+ oldId ;
        db.execSQL(updateValues);

    }


    public void deletePassword(int siteId,String USERTABLE){
        SQLiteDatabase db=this.getWritableDatabase();
        String deleteValue=" DELETE FROM "+ USERTABLE +" WHERE "+ PCOLUMN_SITEID +" = "+ siteId;
        db.execSQL(deleteValue);
    }

}

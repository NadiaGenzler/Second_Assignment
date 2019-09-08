package com.example.secondassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users_db.db";
    public static final String TABLE_NAME = "SUPER_USERS";
    public static final String COL_1_NAME = "NAME";
    public static final String COL_2_GENDER = "GENDER";
    public static final String COL_3_LOCATION = "LOCATION";
    public static final String COL_4_ID = "ID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +
                " ( NAME TEXT, GENDER TEXT, LOCATION TEXT,ID INTEGER PRIMARY KEY AUTOINCREMENT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String gender, String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_NAME, name);
        contentValues.put(COL_2_GENDER, gender);
        contentValues.put(COL_3_LOCATION, location);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        return true;
    }

    public Cursor getAllUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public Cursor getUser(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select id from " + TABLE_NAME , new String[] {id});
        return res;
    }

    public boolean UpdateData( String name, String gender, String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_NAME, name);
        contentValues.put(COL_2_GENDER, gender);
        contentValues.put(COL_3_LOCATION, location);
        db.update(TABLE_NAME,contentValues,"NAME = ?",new String[] {name});
        return true;
    }

    public Integer deleteData(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NAME = ?",new String[] {name});
    }
    public Integer deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"",null);
    }

}
package com.example.fyptest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Chapters.db";
    public static final String TABLE_NAME = "chapters_table";
    public static final String COLUMN__CHAP_ID = "id";
    public static final String COLUMN_CHAP_CONTENT = "content";
    public static final String COLUMN_CHAP_IMAGES = "images";
    private static final String CONTENT_COLUMN_CHAP_CONTENT = "content";
    private static final String CONTENT_TABLE_NAME = "name";
    private HashMap hp;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name , factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table chapters " +
                        "(id integer primary key, content text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS chapters");
        onCreate(db);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chapters where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTENT_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getChapters() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTENT_COLUMN_CHAP_CONTENT)));
            res.moveToNext();
        }
        return array_list;
    }
}
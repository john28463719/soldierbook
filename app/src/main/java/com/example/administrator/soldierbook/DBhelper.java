package com.example.administrator.soldierbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/19.
 */
public class DBhelper extends SQLiteOpenHelper implements DBContent{

    private final static String DATABASE_NAME = "soldier.db";
    private final static int DATABASE_VERSION = 1;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                THING + " CHAR,"+
                TIME + " CHAR);";

        db.execSQL(INIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // 增加單筆資料
    public void addProduct(soldier thing) {
        ContentValues values = new ContentValues();
        values.put(THING, thing.getThing());
        values.put(TIME, thing.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    //編輯單筆資料
    public void editProduct(soldier s,int id) {
        ContentValues values = new ContentValues();
        values.put(THING, s.getThing());
        values.put(TIME, s.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, _ID + "=" + id, null);
        db.close();
    }
    //刪除單筆資料
    public void delProduct(long long_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,_ID +"="+ long_id, null);
        db.close();
    }
    //顯示全資料
    public ArrayList<soldier> getAllProduct(){
        ArrayList<soldier> soldiers = new ArrayList<soldier>();
        String query = "Select * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rows_num = cursor.getCount();
        if(rows_num != 0) {
            cursor.moveToFirst();
            for(int i=0; i<rows_num; i++) {
                soldier soldier = new soldier();
                soldier.setID(Integer.parseInt(cursor.getString(0)));
                soldier.setThing(cursor.getString(1));
                soldier.setTime(cursor.getString(2));
                soldiers.add(soldier);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return soldiers;
    }
    //讀取單筆資料
    public soldier getSoldier(int i){
        String query = "Select * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToPosition(i);
        soldier soldier = new soldier();
        soldier.setID(cursor.getInt(0));
        soldier.setThing(cursor.getString(1));
        soldier.setTime(cursor.getString(2));
        cursor.close();

        return soldier;
    }
    //以時間搜尋列表
    public soldier searchtime(String time){
        soldier soldier = new soldier();
        String s;
        String query = "Select * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rows_num = cursor.getCount();
        if(rows_num != 0) {
            cursor.moveToFirst();
            for(int i=0; i<rows_num; i++) {
                soldier.setID(Integer.parseInt(cursor.getString(0)));
                soldier.setThing(cursor.getString(1));
                soldier.setTime(cursor.getString(2));
                s = soldier.getTime();
                if(time.equals(s)){
                    break;
                }else{
                    soldier.setTime("查無此資料");
                    soldier.setThing("查無此資料");
                }
                cursor.moveToNext();
            }
        }
        cursor.close();

//        int suss = cursor.getColumnIndex(time);
//        cursor.moveToPosition(suss);
//        soldier soldier = new soldier();
//        soldier.setID(cursor.getInt(0));
//        soldier.setThing(cursor.getString(1));
//        soldier.setTime(cursor.getString(2));
//        cursor.close();

        return soldier;
    }
}

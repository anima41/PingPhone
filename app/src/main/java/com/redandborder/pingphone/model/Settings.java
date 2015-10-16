package com.redandborder.pingphone.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.redandborder.pingphone.MyOpenHelper;

public class Settings {
    public String getSkypeId(Context context) {
        String skypeid = null;
        //query method atai
        String[] cols = {"value"}; //hoshi atai
        String selection = "name = ?"; //kensaku suru retu =? ha kimegoto
        String[] selectionArgs = {"skypeid"}; //? no atai
        String groupBy = null;
        String having = null;
        String orderBy = null;
        MyOpenHelper helper = new MyOpenHelper(context);
        final SQLiteDatabase db = helper.getWritableDatabase();

        try {
            Cursor cursor = db.query(helper.TABLE_NAME_SETTINGS, cols, selection, selectionArgs, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                skypeid = cursor.getString(0);
            }
            cursor.close();
        }catch (Exception e){
            Log.e(this.getClass().getName(), e.getMessage());
        }finally{
            db.close();
        }
        return skypeid;
    }

    public String getPass(Context context) {
        String pass = null;
        //query method atai
        String[] cols = {"value"}; //hoshi atai
        String selection = "name = ?"; //kensaku suru retu =? ha kimegoto
        String[] selectionArgs = {"password"}; //? no atai
        String groupBy = null;
        String having = null;
        String orderBy = null;
        MyOpenHelper helper = new MyOpenHelper(context);
        final SQLiteDatabase db = helper.getWritableDatabase();

        try {
            Cursor cursor = db.query(helper.TABLE_NAME_SETTINGS, cols, selection, selectionArgs, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                pass = cursor.getString(0);
            }
            cursor.close();
        }catch (Exception e){
            Log.e(this.getClass().getName(), e.getMessage());
        }finally{
            db.close();
        }
        return pass;
    }
}

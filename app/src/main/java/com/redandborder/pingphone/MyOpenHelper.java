package com.redandborder.pingphone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME_SETTINGS ="settings";
    private static final String DROP_TABLE = "drop table " + TABLE_NAME_SETTINGS + ";";

    public MyOpenHelper(Context context) {
        super(context, "item", null, 3);
      }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME_SETTINGS +"(" + " name text primary key, " + " value text not null " + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
        }catch (Exception e){
            Log.e(this.getClass().getName(), e.getMessage());
        }

        onCreate(db);

    }

}

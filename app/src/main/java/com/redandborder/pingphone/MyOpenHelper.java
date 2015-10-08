package com.redandborder.pingphone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "item", null, 1);
      }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table skype(" + " skype text not null " + ");");
       // db.execSQL("create table history(" + " history text not null " + ");");
        db.execSQL("create table password(" + " password int not null " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

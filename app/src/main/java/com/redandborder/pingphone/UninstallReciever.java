package com.redandborder.pingphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class UninstallReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent){
        MyOpenHelper helper = new MyOpenHelper(context);
        final SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("delete table from "+ helper.TABLE_NAME_SETTINGS + ";");
    }
}

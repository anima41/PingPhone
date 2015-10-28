package com.redandborder.pingphone.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


public class MessageService extends Service {

    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent it, int id, int startId) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Service.ACTIVITY_SERVICE);
        String className = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        String skypeHistory = "com.skype.android.app.mai.HubMaterialActivity";

        if (className.equals(skypeHistory)){
            String warning = "戻るボタンを押してください";
            Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_LONG).show();
        }

        //debug
        //Toast.makeText(getApplicationContext(),className,Toast.LENGTH_SHORT).show();

        //saikidou
        return START_NOT_STICKY;
    }


    public IBinder onBind(Intent intent) {
        return null;
    }
}

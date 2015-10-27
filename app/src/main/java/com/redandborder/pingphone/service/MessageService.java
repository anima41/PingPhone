package com.redandborder.pingphone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


public class MessageService extends Service {

    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent it, int id, int startId) {
         Toast.makeText(getApplicationContext(), "Application", Toast.LENGTH_SHORT).show();

        //saikidou
        return START_NOT_STICKY;
    }


    public IBinder onBind(Intent intent) {
        return null;
    }
}

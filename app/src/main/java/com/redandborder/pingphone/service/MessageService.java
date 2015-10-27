package com.redandborder.pingphone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;


public class MessageService extends Service {
    NotificationManagerCompat manager = null;

    private static final int NOTIFICATION_MINIMUM_ID = 001;

    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent it, int id, int startId) {
         Toast.makeText(getApplicationContext(), "Application", Toast.LENGTH_SHORT).show();
        return NOTIFICATION_MINIMUM_ID;
    }

    public void onDestroy() {

    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}

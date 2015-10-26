package com.redandborder.pingphone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.redandborder.pingphone.R;


public class MessageService extends Service {
    NotificationManagerCompat manager = null;

    private static final int NOTIFICATION_MINIMUM_ID = 001;

    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent it, int id, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(NOTIFICATION_MINIMUM_ID, builder.build());

        //saikidou
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        manager.cancel(NOTIFICATION_MINIMUM_ID);

    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}

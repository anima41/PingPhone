package com.redandborder.pingphone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.redandborder.pingphone.R;

import java.util.Random;


public class MessageService extends Service{
   // NotificationManager notificationManager;
   NotificationManagerCompat manager = null;

    //Random randomMessage;

    //String[] message ={"サービス実行中です",
    //                    "引き続きサービスを実行しています"};

    private static final int NOTIFICATION_MINIMUM_ID = 001;



    public void onCreate(){
        //notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        //randomMessage = new Random();
    }

    @Override
    public int onStartCommand(Intent it,int id,int startId){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(NOTIFICATION_MINIMUM_ID,builder.build());


        //Notification notification = new Notification(R.mipmap.ic_launcher,"Service is running",System.currentTimeMillis());

        //Intent intent = new Intent(this, Standby.class);

        //PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        //notification.setLatestEventInfo(getApplicationContext(), "サービス実行中", "設定画面に移動します", pendingIntent);

        //notificationManager.notify(0,notification);
        //int next = randomMessage.nextInt(message.length);
        //Toast.makeText(this,message[next],Toast.LENGTH_LONG).show();

        //saikidou
        return START_NOT_STICKY;
    }

    public void onDestroy(){
        //notificationManager.cancel(0);
        manager.cancel(NOTIFICATION_MINIMUM_ID);

    }

    public IBinder onBind(Intent intent){
        return null;
    }
}

package com.redandborder.pingphone.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.redandborder.pingphone.R;
import com.redandborder.pingphone.Standby;

import java.util.Random;


public class MessageService extends Service{
    NotificationManager notificationManager;
    Random randomMessage;

    String[] message ={"サービス実行中です",
                        "引き続きサービスを実行しています"};

    public void onCreate(){
        notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        randomMessage = new Random();
    }

    public void onStart(Intent it,int id){
        Notification notification = new Notification(R.mipmap.ic_launcher,"Service is running",System.currentTimeMillis());

        Intent intent = new Intent(this, Standby.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        notification.setLatestEventInfo(getApplicationContext(), "サービス実行中", "設定画面に移動します", pendingIntent);

        notificationManager.notify(0,notification);
        int next = randomMessage.nextInt(message.length);
        Toast.makeText(this,message[next],Toast.LENGTH_LONG).show();
    }

    public void onDestroy(){
        notificationManager.cancel(0);
    }

    public IBinder onBind(Intent intent){
        return null;
    }
}

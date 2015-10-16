package com.redandborder.pingphone;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

// ______________________________________________________________________________
// service nakami
public class TryService extends Service {

    private final static String TAG = "TryService#";

    // Toast count
    private int mCount = 0;

    // Toast hyouji
    private Handler mHandler = new Handler();

    // slead atop
    private boolean mThreadActive = true;

    private Runnable mTask = new Runnable() {

        @Override
        public void run() {

            // active no toki dake
            while (mThreadActive) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        mCount++;
                        showText("カウント:" + mCount);
                    }
                });
            }

            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    showText("スレッド終了");
                }
            });
        }
    };
    private Thread mThread;

    // ______________________________________________________________________________
    /**
     * text hyouji
     * @param text hyouji sitai text
     */
    private void showText(Context ctx, final String text) {
        Toast.makeText(this, TAG + text, Toast.LENGTH_SHORT).show();
    }

    // ______________________________________________________________________________
    /**
     * text hyouji
     * @param text text
     */
    private void showText(final String text) {
        showText(this, text);
    }


    // ______________________________________________________________________________
    @Override
    public IBinder onBind(Intent intent) {
        this.showText("サービスがバインドされました");
        return null;
    }

    // ______________________________________________________________________________
    @Override   // onStartCommand:
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        this.showText("onStartCommand");
        this.mThread = new Thread(null, mTask, "NortifyingService");
        this.mThread.start();

        // tuuchi bar
        showNotification(this);

        // START_NOT_STICKY,START_REDELIVER_INTENT,START_STICKY_COMPATIBILITY
        return START_STICKY;
    }

    // ______________________________________________________________________________
    @Override
    public void onCreate() {
        this.showText("サービスが開始されました");
        super.onCreate();
    }


    // ______________________________________________________________________________
    @Override   // onDestroy:
    public void onDestroy() {
        this.showText("サービスが終了されました");

        // thread stop
        this.mThread.interrupt();
        this.mThreadActive = false;

        this.stopNotification(this);
        super.onDestroy();
    }

    // ______________________________________________________________________________
    // no tuuchi bar
    private void stopNotification(final Context ctx) {
        NotificationManager mgr = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
       // mgr.cancel(R.layout.activity_service);
    }

    // ______________________________________________________________________________
    // on tuuchi bar
    private void showNotification(final Context ctx) {

        NotificationManager mgr = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(ctx, Standby.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // tuuchi bar no naiyou
        Notification n = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("サービスが起動しました")
                .setWhen(System.currentTimeMillis())    // time
                .setContentTitle("サービス起動中")
                .setContentText("このバーをタップ後に「サービス終了」を選択してください")
                .setContentIntent(contentIntent)// intext
                .build();
        n.flags = Notification.FLAG_NO_CLEAR;

       // mgr.notify(R.layout.activity_service, n);

    }


}


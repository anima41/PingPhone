package com.redandborder.pingphone.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

// ______________________________________________________________________________
// service nakami
public class ToastService extends Service {

    private final static String TAG = "TryService#";


        // Toast count
        private int mCount = 0;

        private Handler mHandler = new Handler();

        // slead atop
        private boolean mThreadActive = true;

        ActivityManager activityManager = (ActivityManager) getSystemService(Service.ACTIVITY_SERVICE);
        String className = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();

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
                            //mCount++;
                            //showText("カウント:" + mCount);
                            showText(className);
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
    /*
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

        //this.stopNotification(this);
        super.onDestroy();
    }

}




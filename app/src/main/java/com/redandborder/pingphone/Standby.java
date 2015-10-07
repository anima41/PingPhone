package com.redandborder.pingphone;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Standby extends Activity {

/*    private Handler mHandler;
    private Timer mTimer;
    // time format
    private static SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("yyyy年　MM月dd日　HH:mm:ss");
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standby);

        // keep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

/*            mHandler = new Handler(getMainLooper());
            mTimer = new Timer();

            //1s gotoni run
            mTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        public void run() {
                            Calendar calendar = Calendar.getInstance();
                            String nowDate = mSimpleDataFormat.format(calendar.getTime());
                            // TextView shutoku
                            ((TextView) findViewById(R.id.standby_time)).setText(nowDate);
                        }
                    });}
            },0,1000);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // gamen ga kawaru toki cancel
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
*/
        }

    }


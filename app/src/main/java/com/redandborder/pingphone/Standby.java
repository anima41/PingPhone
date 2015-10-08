package com.redandborder.pingphone;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Standby extends Activity implements OnClickListener {

    private Handler mHandler;
    private Timer mTimer;
    // time format
    private static SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("yyyy年　MM月dd日　HH:mm:ss");

    //button_down set
    protected Drawable btn_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standby);

        // keep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // taskbar nashi
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // button set
        Button btn = (Button) findViewById(R.id.skype_button); //ToDO koko ni kaite iinoka
        btn.setOnClickListener(this);


        mHandler = new Handler(getMainLooper());
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
                        TextView watchText = (TextView) findViewById(R.id.standby_watch);
                        watchText.setText(nowDate);
                        // TextView ichi
                        watchText.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                });
            }
        }, 0, 1000);
    }

    public void onClick(View v) {
        // button set
        Button btn = (Button) findViewById(R.id.skype_button); //ToDo muda deha?
        //layout set
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.standby_layout);
        //button_down set
        Resources res = getResources();
        btn_down = (Drawable)res.getDrawable(R.drawable.button_down);

        if (v == btn) {
            layout.setBackgroundColor(Color.DKGRAY);
            btn.setBackground(btn_down);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // gamen ga kawaru toki cancel
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

    }

}


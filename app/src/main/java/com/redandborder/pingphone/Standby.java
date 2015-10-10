package com.redandborder.pingphone;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    protected Drawable btn_up;

    //menu button
    private Button btn_menu;

    private static final String TAG = "Standby";


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
        btn_down = (Drawable) res.getDrawable(R.drawable.button_down);

        if (v == btn) {
            // btn tap
            layout.setBackgroundColor(Color.DKGRAY);
            btn.setBackground(btn_down);

            skypeCall("aq_aqua", this);//ToDo kaeru

        }
    }

    // skypeCall no nakami
    private static void skypeCall(String name, Context ctx) {
        try {
            // skype nakatta baai goToMarket yobidasi
            if (!isSkypeClientInstalled(ctx)) {
                goToMarket(ctx);
                return;
            }

            // inten sakusei
            Intent skype_intent = new Intent("android.intent.action.VIEW");
            Uri uri = Uri.parse("skype:" + name);
            // intent set
            skype_intent.setData(uri);
            // start
            ctx.startActivity(skype_intent);


        } catch (ActivityNotFoundException e) {  //ToDo error no baai kaesu monoha?
            //not install no baai errror rog
            Log.e("SKYPE CALL", "Skype failed", e);
        }

    }

    // skype ga aruka kakunin
    private static boolean isSkypeClientInstalled(Context ctx) {
        // hensuu sengen
        PackageManager myPackageMgr = ctx.getPackageManager();
        try {
            // skype ga aruka kakunin
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
            // nakatta baai false wo kaesu
        } catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        // atta baai true wo kaesu
        return (true);

    }

    private static void goToMarket(Context ctx) {
        // google play no URi shutoku
        Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=com.skype.raider");
        // intent sakusei
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        // new_task de kidou
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(myIntent);

        return;
    }


    //menu set
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    //menu nakami
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_skype:
                Intent intent1 = new Intent(Standby.this, SkypeSetting.class);
                startActivity(intent1);
                Standby.this.finish();
                return true;

            case R.id.menu_history:
                Intent intent2 = new Intent(Standby.this, History.class);
                startActivity(intent2);
                Standby.this.finish();
                return true;

            case R.id.menu_passward:
                Intent intent3 = new Intent(Standby.this, PasswordSetting.class);
                startActivity(intent3);
                Standby.this.finish();
                return true;

            default:
                break;
        }
        return false;
    }


    protected void onResume() {
        super.onResume();

        //iro chage
        // button set
        Button btn = (Button) findViewById(R.id.skype_button); //ToDo muda deha?
        //layout set
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.standby_layout);
        //button_up set
        Resources res = getResources();
        btn_up = (Drawable) res.getDrawable(R.drawable.button_up);

        layout.setBackgroundColor(Color.WHITE);
        btn.setBackground(btn_up);

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


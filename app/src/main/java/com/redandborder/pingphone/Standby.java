package com.redandborder.pingphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.redandborder.pingphone.model.Settings;

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

    private static final String TAG = "Standby";
    private Button callButton;
    private Button menuSetBtn;

    final EditText editView = new EditText(Standby.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standby);

        // keep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // taskbar nashi
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // button set //ToDo choufuku siteru
        callButton = (Button) findViewById(R.id.skype_button);
        callButton.setOnClickListener(this);

        menuSetBtn = (Button) findViewById(R.id.settingsMenuButton);
        menuSetBtn.setOnClickListener(this);

        editView.setOnFocusChangeListener(this);

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
         //layout set
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.standby_layout);
        //button_down set
        Resources res = getResources();
        btn_down = (Drawable) res.getDrawable(R.drawable.button_down);


        if (v == callButton) {
            // btn tap
            layout.setBackgroundColor(Color.DKGRAY);
            callButton.setBackground(btn_down);

            //skype call
            Settings settings = new Settings();
            String idText = settings.getSkypeId(this);

            skypeCall(idText, this);

        }else if (v == menuSetBtn){

            //Dialog
            new AlertDialog.Builder(Standby.this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("パスワードを入力してください")
                    .setView(editView)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Settings settings = new Settings();
                            String pass = settings.getPass(Standby.this);

                            String inputPass = editView.getText().toString();

                            if(pass.equals(inputPass)) {
                                //match yes
                                Intent intent = new Intent(Standby.this, SettingsMenu.class);
                                startActivity(intent);
                            }else {
                                //match no
                                Toast.makeText(Standby.this,
                                        "パスワードが違います",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
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
            //not install no baai error rog
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // on focus
        if (hasFocus) {
            // keyboard show
            inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }



    protected void onResume() {
        super.onResume();

        //iro chage
        //layout set
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.standby_layout);
        //button_up set
        Resources res = getResources();
        btn_up = (Drawable) res.getDrawable(R.drawable.button_up);

        layout.setBackgroundColor(Color.WHITE);
        callButton.setBackground(btn_up);

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


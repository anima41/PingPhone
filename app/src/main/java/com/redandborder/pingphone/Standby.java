package com.redandborder.pingphone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.redandborder.pingphone.model.Settings;
import com.redandborder.pingphone.service.MessageService;
import com.redandborder.pingphone.util.MailUtil;
import com.redandborder.pingphone.util.MeasurementGAManager;
import com.redandborder.pingphone.util.ToastUtil;

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

    private Intent intent = null;

    private AlarmManager alarmManager =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standby);

        //Anrytics
        MeasurementGAManager.sendGAScreen(this, "Standby");

        // keep screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // taskbar nashi
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // button set //ToDo choufuku siteru
        callButton = (Button) findViewById(R.id.skype_button);
        callButton.setOnClickListener(this);

        menuSetBtn = (Button) findViewById(R.id.settingsMenuButton);
        menuSetBtn.setOnClickListener(this);

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

        //pass nashi no baai
        Settings settings = new Settings();
        String pass = settings.getPass(Standby.this);
        Intent intent = null;

        if (TextUtils.isEmpty(pass)) {
            intent = new Intent(Standby.this, PasswordSetting.class);
            startActivity(intent);
        }



    }



    public void onClick(View v) {
        //button_down set
        Resources res = getResources();
        btn_down = (Drawable) res.getDrawable(R.drawable.button_down);


        if (v == callButton) {
            // btn tap
            callButton.setBackground(btn_down);

            //skype call
            Settings settings = new Settings();
            String idText = settings.getSkypeId(this);

            skypeCall(idText, this);

            Context context = getApplicationContext();
            intent = new Intent(context,MessageService.class);
            startService(intent);

            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            long time = Calendar.getInstance().getTimeInMillis();
            alarmManager =(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time, 2 * 1000,
                    pendingIntent);

        } else if (v == menuSetBtn) {
            final EditText editView = new EditText(Standby.this);

            //kisoku
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
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

                            if (pass.equals(inputPass)) {
                                //match yes
                                Intent intent = new Intent(Standby.this, SettingsMenu.class);
                                startActivity(intent);
                            } else {
                                //match no
                                ToastUtil toastUtil = new ToastUtil();
                                toastUtil.show(Standby.this, "パスワードが違います");
                            }
                        }
                    })
                    .setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    })
                    .setNegativeButton("お忘れの方", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //MailUtil mailUtil =  new MailUtil(Standby.this);
                            //mailUtil.execute();
                            ToastUtil toastUtil = new ToastUtil();
                            toastUtil.show(Standby.this, "アンインストールするとパスワードがリセットされます");

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


    protected void onResume() {
        super.onResume();
        //iro chage
        //button_up set
        Resources res = getResources();
        btn_up = (Drawable) res.getDrawable(R.drawable.button_up);
        callButton.setBackground(btn_up);

        if (alarmManager != null){
            PendingIntent pendingIntent = PendingIntent.getService(this,0,intent,0);
            alarmManager.cancel(pendingIntent);
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

        if (alarmManager != null){
            PendingIntent pendingIntent = PendingIntent.getService(this,0,intent,0);
            alarmManager.cancel(pendingIntent);
        }

    }

}


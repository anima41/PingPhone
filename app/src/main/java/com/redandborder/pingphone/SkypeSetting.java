package com.redandborder.pingphone;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.redandborder.pingphone.model.Settings;
import com.redandborder.pingphone.util.ToastUtil;


public class SkypeSetting extends ActionBarActivity {
    //hensu shokika
    private Handler hdl = null;
    private SplashHandler r = null;

    //private static final String TAG = "SkypeSetting";
    private Context mContext;
    private EditText etRecepient;
    private Button btnSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skype_setting);

        TextView tvDefault = (TextView) findViewById(R.id.skype_default);
        String default_text = new Settings().getSkypeId(this);
        tvDefault.setText(default_text);

        if (TextUtils.isEmpty(default_text)) {
            TextView defaultTitle = (TextView) findViewById(R.id.tv_skype_default);
            defaultTitle.setVisibility(View.GONE);
        }

        //initialize jikkou
        initialize();
    }

    private void initialize() {
        //hensuu sengen
        mContext = this;
        etRecepient = (EditText) findViewById(R.id.id_text);
        btnSet = (Button) findViewById(R.id.skype_set);
        btnSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //editText sengen
                EditText et = (EditText) findViewById(R.id.id_text);

                //moji wo toru
                SpannableStringBuilder sb = (SpannableStringBuilder) et.getText();
                String skypeID = sb.toString();

                if (TextUtils.isEmpty(skypeID) || skypeID.length() == 0 || skypeID.trim().equals("")){
                    //text nashi no baai
                        ToastUtil toastUtil = new ToastUtil();
                        toastUtil.show(SkypeSetting.this, "スカイプ名を設定してください");
                } else {
                    //text ok
                    //sql sengen
                    MyOpenHelper helper = new MyOpenHelper(SkypeSetting.this);
                    final SQLiteDatabase db = helper.getWritableDatabase();
                    //sql ni set
                    String sql = "INSERT OR REPLACE INTO settings (name,value) VALUES ('skypeid','" + skypeID + "');";
                    db.execSQL(sql);
                    db.close();

                    ToastUtil toastUtil = new ToastUtil();
                    toastUtil.show(SkypeSetting.this, "登録しました");

                    //instans
                    hdl = new Handler();
                    r = new SplashHandler();
                    //2hikisu de sitei
                    hdl.postDelayed(r, 2000);
                }
            }
        });
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

    // skype ga not install no baai
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

    // skypeCall no nakami
    private static void btnSet(String name, Context ctx) {
        try {
            // skype nakatta baai goToMarket yobidasi
            if (!isSkypeClientInstalled(ctx)) {
                goToMarket(ctx);
                return;
            }

            // intent sakusei
            Intent skype_intent = new Intent("android.intent.action.VIEW");
            Uri uri = Uri.parse("skype:" + name); //name dokode shutoku siterunoka?
            // intent set
            skype_intent.setData(uri);
            // start
            ctx.startActivity(skype_intent);
        } catch (ActivityNotFoundException e) {
            //not install no baai errror rog
            Log.e("SKYPE CALL", "Skype failed", e);
        }

    }

    // SplashHandler
    class SplashHandler implements Runnable {
        public void run() {
            Intent intent = new Intent(SkypeSetting.this, Standby.class);
            startActivity(intent);
            SkypeSetting.this.finish();
        }
    }
}

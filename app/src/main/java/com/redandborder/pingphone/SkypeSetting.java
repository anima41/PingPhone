package com.redandborder.pingphone;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SkypeSetting extends ActionBarActivity {

    private static final String TAG = "SkypeSetting";
    private Context mContext;
    private EditText etRecepient;
    private Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skype_setting);

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

                //sql sengen
                MyOpenHelper helper = new MyOpenHelper(SkypeSetting.this);
                final SQLiteDatabase db = helper.getWritableDatabase();

                //editText sengen
                EditText et = (EditText)findViewById(R.id.id_text);

                //moji wo toru
                SpannableStringBuilder sb = (SpannableStringBuilder)et.getText();
                String skypeID = sb.toString();

                //sql ni set
                String sql = "UPDATE skype SET skype = ('"+ skypeID +"') ;";
                db.execSQL(sql);

                //debug
                Toast toast = Toast.makeText(SkypeSetting.this, sql, Toast.LENGTH_LONG);
                toast.show();


                //edittext wo shutoku site call
                //btnSet(etRecepient.getText().toString(), mContext);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // tukattenai
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

}

package com.redandborder.pingphone;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.redandborder.pingphone.model.Settings;

import java.util.regex.Pattern;


public class PasswordSetting extends ActionBarActivity implements OnClickListener{
    //hensu shokika
    private Handler hdl = null;
    private SplashHandler r = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);

        // button set
        Button btn = (Button) findViewById(R.id.pass_set_button); //ToDo hituyouka
        btn.setOnClickListener(this);

    }

    public void onClick(View view){
        // button set
        Button btn = (Button) findViewById(R.id.pass_set_button); //ToDo doko ni kakuka
        btn.setOnClickListener(this);

        //moji no shutoku
        //editText sengen
        EditText et1 = (EditText)findViewById(R.id.pass_before);
        EditText et2 = (EditText)findViewById(R.id.pass_after);
        //moji wo toru
        SpannableStringBuilder sb1 = (SpannableStringBuilder)et1.getText();
        SpannableStringBuilder sb2 = (SpannableStringBuilder)et2.getText();
        //moji wo toru
        String t_before = sb1.toString();
        String t_after = sb2.toString();

        if (t_before.equals(t_after)) {
            //equals datta baai NG
            String warning = getResources().getString(R.string.warning_equals);
            Toast toast_w = Toast.makeText(PasswordSetting.this, warning, Toast.LENGTH_LONG);
            toast_w.show();

        }else{
            //ok
            MyOpenHelper helper = new MyOpenHelper(PasswordSetting.this);
            final SQLiteDatabase db = helper.getWritableDatabase();

            String sql = "INSERT OR REPLACE INTO " + helper.TABLE_NAME_SETTINGS +" (name,value) VALUES ('password','" + t_after + "');";
            db.execSQL(sql);
            db.close();

            //toast
            String change = getResources().getString(R.string.pass_change);
            Toast toast_c = Toast.makeText(PasswordSetting.this, change, Toast.LENGTH_LONG);
            toast_c.show();

            //instans
            hdl = new Handler();
            r = new SplashHandler();
            //2hikisu de sitei
            hdl.postDelayed(r, 3000);
        }

    }

    // SplashHandler
    class SplashHandler implements Runnable {
        public void run() {
            Settings settings = new Settings();
            String skypeId = settings.getSkypeId(PasswordSetting.this);
            Intent intent = null;

            if (TextUtils.isEmpty(skypeId)) {
                //skypeID null datta baai
                intent = new Intent(PasswordSetting.this, SkypeSetting.class);
            }else {
                //skypeID areba
                intent = new Intent(PasswordSetting.this, Standby.class);
            }
            startActivity(intent);
            PasswordSetting.this.finish();
        }
    }
}

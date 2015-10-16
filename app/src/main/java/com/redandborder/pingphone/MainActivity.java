package com.redandborder.pingphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.redandborder.pingphone.model.Settings;


public class MainActivity extends Activity {

    //hensu shokika
    private Handler hdl = null;
    private SplashHandler r = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instans
        hdl = new Handler();
        r = new SplashHandler();
        //2hikisu de sitei
        hdl.postDelayed(r, 3000);
    }

    // SplashHandler
    class SplashHandler implements Runnable {
        public void run() {
            Settings settings = new Settings();
            String pass = settings.getPass(MainActivity.this);
            Intent intent = null;

            if (TextUtils.isEmpty(pass)) {
                intent = new Intent(MainActivity.this, PasswordSetting.class);
            }else {
                intent = new Intent(MainActivity.this, Standby.class);
            }
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

}

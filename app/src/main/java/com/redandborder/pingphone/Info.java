package com.redandborder.pingphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Info extends ActionBarActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button btn = (Button) findViewById(R.id.info_button);
        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this,PasswordSetting.class);
        startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            this.finish();
            this.moveTaskToBack(true);
            return false;
        }
    }
}

package com.redandborder.pingphone;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class PasswordMenu extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_menu);

        Button pwSet = (Button) findViewById(R.id.passward_setting);
        pwSet.setOnClickListener(this);

        Button maSet = (Button) findViewById(R.id.mail_setting);
        maSet.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passward_setting:
                Intent intent1 = new Intent(PasswordMenu.this, PasswordSetting.class);
                startActivity(intent1);
                PasswordMenu.this.finish();
                break;

            case R.id.mail_setting:
                Intent intent2 = new Intent(PasswordMenu.this, MailSetting.class);
                startActivity(intent2);
                PasswordMenu.this.finish();
                break;

            default:
                break;
        }
    }
}
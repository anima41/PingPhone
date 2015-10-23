package com.redandborder.pingphone;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MailSetting extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_setting);

        Button maSet = (Button)findViewById(R.id.mail_set_button);
        maSet.setOnClickListener(this);

        Button maSkip = (Button)findViewById(R.id.mail_skip_button);
        maSkip.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mail_set_button:
                //db hozon

                Intent intent1 = new Intent(MailSetting.this, Standby.class);
                startActivity(intent1);
                MailSetting.this.finish();
                break;

            case R.id.mail_skip_button:
                Intent intent2 = new Intent(MailSetting.this, Standby.class);
                startActivity(intent2);
                MailSetting.this.finish();
                break;

            default:
                break;
        }
    }


}

package com.redandborder.pingphone;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SettingsMenu extends ActionBarActivity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        //ToDo hoka ni kakikata aru nodeha?
        Button menuHistory = (Button)findViewById(R.id.menu_history);
        menuHistory.setOnClickListener(this);

        Button menuSkype = (Button)findViewById(R.id.menu_skype);
        menuSkype.setOnClickListener(this);

        Button menuPass = (Button)findViewById(R.id.menu_passward);
        menuPass.setOnClickListener(this);

    }

    public void onClick (View v){
        switch (v.getId()) {
            case R.id.menu_history:
                break;

            case R.id.menu_skype:
                Intent intent2 = new Intent(SettingsMenu.this, SkypeSetting.class);
                startActivity(intent2);
                SettingsMenu.this.finish();
                break;

            case R.id.menu_passward:
                Intent intent3 = new Intent(SettingsMenu.this, PasswordSetting.class);
                startActivity(intent3);
                SettingsMenu.this.finish();
                break;

            default:
                break;
        }
    }
}

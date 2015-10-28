package com.redandborder.pingphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redandborder.pingphone.model.Settings;

public class Debug extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        Settings settings = new Settings();
        String skypeid = settings.getSkypeId(this);
        TextView textView = new TextView(this);
        textView.setText(skypeid);
        layout.addView(textView);

        final Button button = new Button(this);
        button.setText("OK");
        layout.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == button) {
                    Intent intent = new Intent(Debug.this, SkypeSetting.class);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }
}

package com.redandborder.pingphone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Debug extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        try {

            Cursor c = db.query(helper.TABLE_NAME_SETTINGS, new String[]{"name", "value"}, null,
                    null, null, null, null);

            boolean mov = c.moveToFirst();
            while (mov) {
                TextView textView = new TextView(this);
                textView.setText(String.format("%s : %s", c.getString(0), c.getString(1)));
                mov = c.moveToNext();
                layout.addView(textView);
            }

            c.close();
            db.close();
        }catch (Exception e){
            Log.e(this.getClass().getName(), e.getMessage());
        }

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

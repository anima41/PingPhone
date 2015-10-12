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

        //query method atai
        String[] cols = {"value"}; //hoshi atai
        String selection = "name = ?"; //kensaku suru retu =? ha kimegoto
        String[] selectionArgs = {"skypeid"}; //? no atai
        String groupBy = null;
        String having = null;
        String orderBy = null;
        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        try {
            Cursor cursor = db.query(helper.TABLE_NAME_SETTINGS, cols, selection, selectionArgs, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                TextView textView = new TextView(this);
                textView.setText(cursor.getString(0) );
                layout.addView(textView);
            }
            cursor.close();
        }catch (Exception e){
                Log.e(this.getClass().getName(), e.getMessage());
        }finally{
            db.close();
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

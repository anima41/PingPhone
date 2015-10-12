package com.redandborder.pingphone;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


public class PasswordSetting extends ActionBarActivity implements OnClickListener{

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

        if (t_before.equals(t_after)){
            //warning
            String warning = getResources().getString(R.string.warning_equals);
            Toast toast_w = Toast.makeText(PasswordSetting.this, warning, Toast.LENGTH_LONG);
            toast_w.show();
        }else{
            //change

            //sql ni set
            MyOpenHelper helper = new MyOpenHelper(PasswordSetting.this);
            final SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "UPDATE password SET password = ('"+ t_after +"') ;";
            db.execSQL(sql);

            //toast
            String change = getResources().getString(R.string.pass_change);
            Toast toast_c = Toast.makeText(PasswordSetting.this, change, Toast.LENGTH_LONG);
            toast_c.show();
        }


    }

}

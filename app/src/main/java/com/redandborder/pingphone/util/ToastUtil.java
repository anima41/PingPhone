package com.redandborder.pingphone.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.redandborder.pingphone.R;

public class ToastUtil extends Activity{
    public void toastLayout(String toastText) {
        // inflater
        LayoutInflater inflater = getLayoutInflater();
        // xml
        View layout = inflater.inflate(R.layout.toast_layout, null);
        TextView tv = (TextView) layout.findViewById(R.id.toastText);

        tv.setText(toastText);
        Toast tst = new Toast(this);
        tst.setView(layout);
        tst.show();
    }
}

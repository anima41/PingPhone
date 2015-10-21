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


        Toast toast_w = new Toast(this);
        toast_w.setView(layout);
        toast_w.show();
    }
}

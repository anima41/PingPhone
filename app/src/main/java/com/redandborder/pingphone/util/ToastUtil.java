package com.redandborder.pingphone.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.redandborder.pingphone.R;

public class ToastUtil {
    public void show(Activity act,String toastText) {
        // inflater
        LayoutInflater inflater = act.getLayoutInflater();
        // xml
        View layout = inflater.inflate(R.layout.toast_layout, null);
        TextView tv = (TextView) layout.findViewById(R.id.toastText);

        tv.setText(toastText);
        Toast tst = new Toast(act);
        tst.setView(layout);
        tst.show();
    }
}

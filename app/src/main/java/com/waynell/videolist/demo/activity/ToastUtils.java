package com.waynell.videolist.demo.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by renzhenming on 2017/5/17.
 */

public class ToastUtils extends Toast {

    private static Toast toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastUtils(Context context) {
        super(context);
    }

    public static void showToast(Context context,String value){
        if (toast == null)
            toast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        toast.setText(value);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}

package com.waynell.videolist.demo.activity;

import android.util.Log;

/**
 * Created by renzhenming on 2017/5/25.
 */

public final class LogUtils {

    //开关
    private final static boolean flag = true;//true 测试                 false  上线

    public static void v(String tag,String msg){
        if(flag){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag,String msg){
        if(flag){
            Log.v(tag, msg);
        }
    }
    public static void i(String tag,String msg){
        if(flag){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag,String msg){
        if(flag){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag,String msg){
        if(flag){
            Log.e(tag, msg);
        }
    }
}


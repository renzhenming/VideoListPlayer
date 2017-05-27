package com.waynell.videolist.demo;

import android.app.Application;
import android.os.Environment;

import java.io.PrintWriter;

/**
 * Created by renzhenming on 2017/5/27.
 */

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
    }
    class MyHandler implements Thread.UncaughtExceptionHandler {

        //一旦出现未捕获的崩溃异常, 就会自动走到此方法中
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();

            //收集错误日志, 也可以将日志自动上传到服务器
            try {
                PrintWriter writer = new PrintWriter(Environment
                        .getExternalStorageDirectory().getAbsolutePath()+ "/aaaaaaaaaaaaaaaa.log");
                ex.printStackTrace(writer);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //强制结束当前进程, 闪退
            //System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }
}

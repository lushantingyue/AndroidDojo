package cn.lushantingyue.rxjavademo;

import android.app.Application;

/**
 * Created by Administrator on 2018/1/9 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        Logger.init("retrofit_demo")                 // default PRETTYLOGGER or use just init()
//                .setMethodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
//                .setMethodOffset(2);                // default 0
    }
}

package yss.com.myrongappication.application;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016-11-10.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化融云
         */
        RongIM.init(this);
    }
}

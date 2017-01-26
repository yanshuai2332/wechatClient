package yss.com.myrongappication.application;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hliman on 2016/5/10 0010.
 * email:hliman@163.com
 */
public class ExitApplication extends Application {
    private List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;

    private ExitApplication() {
    }

    // 单例模式中获取唯一的MyApplication实例
    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void finishActivity(Class activityClass) {
        for (Activity activity : activityList) {
            if (activity.getClass() == activityClass) {
                activity.finish();
            }
        }

    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
    }

    public boolean acitivtyIsExist(Class activityClass) {
        for (Activity activity : activityList) {
            if (activity != null)
                if (activity.getClass() == activityClass)
                    return true;
        }
        return false;
    }
}
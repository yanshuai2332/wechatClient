package yss.com.myrongappication.fast;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.orm.SugarContext;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import yss.com.myrongappication.friendcircle.friendcircledemo.application.BaseApplication;
import yss.com.myrongappication.friendcircle.friendcircledemo.constants.CommonConstants;
import yss.com.myrongappication.util.DemoContext;
/**
 * Created by Bob on 15/8/18.
 */
public class App extends Application implements RongIM.UserInfoProvider{
    private static Context mContext;
    private static Handler mHandler;
    private static Thread mMainThread;
    private static int mMainThreadId;
    private static App instance;
    @Override
    public void onCreate() {

        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        instance = this;
        initFriendCircle();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        rongInit();
        DemoContext.init(this);
        RongIM.setUserInfoProvider(this, true);//设置用户信息提供者。
        SugarContext.init(this);




    }

    private void initFriendCircle() {
        // 配置ImageLoad
        File cacheDir = new File(CommonConstants.SDPATH_IMAGE);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(2)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(),
                                5 * 1000, 30 * 1000)).writeDebugLogs()
                .defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);
    }

    private void rongInit() {
        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);

            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

                DemoContext.init(this);
            }
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
    @Override
    public UserInfo getUserInfo(String s) {
        if (DemoContext.getInstance() == null)
            return null;
        return DemoContext.getInstance().getUserInfoByUserId(s);
        //return new UserInfo("a0bddfe9-abc9-11e6-8424-408d5c7878fd","颜帅",Uri.parse("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2157537042,3590938171&fm=116&gp=0.jpg"));

    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }


    /** 获取上下文对象 */
    public static Context getContext() {
        return mContext;
    }

    /** 获取Handler对象 */
    public static Handler getHandler() {
        return mHandler;
    }

    /** 获取主线程对象 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static App getInstance() {
        return instance;
    }
}

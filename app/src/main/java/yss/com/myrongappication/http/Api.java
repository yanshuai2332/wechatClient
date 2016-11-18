package yss.com.myrongappication.http;

import android.content.Context;

/**
 * 用同一个访问
 * Created by hliman on 2015/10/5.
 */
public class Api {
    public static ManagerHttpApiInterface getApi(Context context) {
        ManagerHttp managerHttp = new ManagerHttp();
        return managerHttp.get();
    }
    
}

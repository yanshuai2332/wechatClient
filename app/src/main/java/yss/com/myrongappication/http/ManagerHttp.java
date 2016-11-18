package yss.com.myrongappication.http;

/**
 * 用于的联网类
 * 配置跟路径和访问的接口
 * Created by hliman on 2015/10/5.
 */
public class ManagerHttp extends BaseHttp<ManagerHttpApiInterface> {
//    public static final String root = "http://www.shequyouwo.com:12690";

         public static final String root="http://192.168.1.166:8080";
//    public static final String root="http://192.168.1.203:8080";

    @Override
    public String getBaseUrl() {
        return root;
    }

    @Override
    public Class<ManagerHttpApiInterface> getTClass() {
        return ManagerHttpApiInterface.class;
    }
}

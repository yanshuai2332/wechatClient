package yss.com.myrongappication.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络连接的过滤器
 * Created by hliman on 2016/6/15 0015.
 * email:hliman@163.com
 */
public class SimpleErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Response response=chain.proceed(request);
        if(response.code()!=200){
            throw new IOException("返回"+response.code(),new Exception("返回"+response.code()));
        }
        return response;
    }
}

package yss.com.myrongappication.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hliman on 2016/6/6 0006.
 * email:hliman@163.com
 */
public abstract class BaseHttp<T> {
    private T restClient;

    public T get() {
        return getRestClient();
    }

    private T getRestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        SimpleErrorInterceptor simpleErrorInterceptor=new SimpleErrorInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.readTimeout(8, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.addInterceptor(simpleErrorInterceptor);
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        restClient = retrofit.create(getTClass());
        return restClient;
    }

    protected abstract Class<T> getTClass();

    protected abstract String getBaseUrl();

}

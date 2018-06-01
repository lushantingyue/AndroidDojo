package cn.lushantingyue.materialdesign_demo.api;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import cn.lushantingyue.materialdesign_demo.BuildConfig;
import cn.lushantingyue.materialdesign_demo.utils.Constant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lushantingyue on 2017/12/28 17.
 * Responsibilities: Request network api data
 * Description: Wrapper of Retrofit
 * ProjectName:
 */

public class RetrofitWrapper {

//    private
    final Retrofit retrofit;
    final ApiService service;

    RetrofitWrapper() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.i(message);
            }
        });
        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(9, TimeUnit.SECONDS);
        builder.addInterceptor(logInterceptor);
        builder.addNetworkInterceptor(new MockInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    // TODO: 2018/6/1   动态添加header
    RetrofitWrapper(Interceptor interceptor) {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.i(message);
            }
        });
        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(9, TimeUnit.SECONDS);
        builder.addInterceptor(logInterceptor);
        builder.addNetworkInterceptor(new MockInterceptor());
        builder.addNetworkInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public ApiService getService() {
        return service;
    }

    private static class SingletonHolder {
        private static final ApiService instance = new RetrofitWrapper().getService();
    }

    public static ApiService getInstance() {
        return SingletonHolder.instance;
    }

    public static ApiService getInstance(Interceptor interceptor) {
        return new RetrofitWrapper(interceptor).getService();
    }

}

package cn.lushantingyue.materialdesign_demo.api;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Lushantingyue on 2018/1/24 17.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class TokenInterceptor implements Interceptor {

    private String token;

    public TokenInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // TODO: 2018/6/1  json web token 认证
        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.addHeader("Authorization", token);

        Request request = requestBuilder.build();

        return chain.proceed(request);
    }
}

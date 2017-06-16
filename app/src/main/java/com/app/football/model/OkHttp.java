package com.app.football.model;

import com.app.football.util.MyUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/10.
 */

public class OkHttp {

    private OkHttpClient mClient;

    public OkHttp() {
        mClient = new OkHttpClient.Builder()
                .cache(MyUtil.sCache)
                .addInterceptor(new MyInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public String getData(String url) throws IOException {
        // 可能会出现java.lang.IllegalArgumentException: unexpected url:
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mClient
                .newCall(request)
                .execute();

        return response.body().string();
    }

    private class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (MyUtil.isNetworkConnected()) {
                Response response = chain.proceed(request);
                // 懂球帝服务器默认的应该是30s,这里不做处理。
                return response;
            } else {
                //在没有网络时，强制使用缓存
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Response response = chain.proceed(request);
                // 有效时间尽量长一点。
                int maxStale = 60 * 60 * 24 * 356;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }
}

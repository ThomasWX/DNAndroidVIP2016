package com.dn.open.framework.proxy.http;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 产品认为，Volley对大图片的加载比较慢，性能不好，所以需改用OkHttp
 *
 * okHttp 线程的坑：其onResponse、onFailure是运行于异步线程
 */
public class OkHttpProcessor implements IHttpProcessor{
    private static final String TAG = OkHttpProcessor.class.getSimpleName();
    private final Handler mHandler;
    private OkHttpClient okHttpClient;

    public OkHttpProcessor() {
        okHttpClient = new OkHttpClient();
        // OkHttp是单独创建了一个线程来运行，所以要用Handler
        mHandler = new Handler();
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback callback) {
        RequestBody requestBody =  appendBody(params);
        /*final Request request = new Request().newBuilder().url(url)
                .post(requestBody)
                .build();*/
        // 上面这样写会报 403 forbid 网络异常，而request 中的参数和 Volley 中的都一样，那么必然是OkHttp 的问题
        final Request request = new Request().newBuilder().url(url)
                .post(requestBody)
                .header("User-Agent","a") // 中国很多网站，包括网易会禁止没有带header的请求，所以要加上header
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String error = e.toString();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(error);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                final boolean isSuccess = response.isSuccessful();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess){
                            callback.onSuccess(result);
                        } else {
                            callback.onFailure(result);
                        }
                    }
                });


            }
        });

    }

    private RequestBody appendBody(Map<String,Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null || params.isEmpty()) return body.build();
        for (Map.Entry<String, Object> entity :
                params.entrySet()) {
            body.add(entity.getKey(),entity.getValue().toString());
        }
        return body.build();
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {

    }
}

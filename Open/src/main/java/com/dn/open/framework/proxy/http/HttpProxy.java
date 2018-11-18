package com.dn.open.framework.proxy.http;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 相当于第三步
 *
 * 全局只需要有一个 HttpProxy 实例，所以做成单例
 */
public class HttpProxy implements IHttpProcessor{
    // 被代理的对象
    private static IHttpProcessor iHttpProcessor = null;

    // 网络请求参数
    private Map<String,Object> mParams;
    private static HttpProxy instance;
    private HttpProxy(){
        mParams = new HashMap<>();
    }

    public static HttpProxy obtain(){
        synchronized (HttpProxy.class){
            if (instance == null){
                instance = new HttpProxy();
            }
            return instance;
        }
    }


    /**
     * 专门用于设置代理的接口
     */
    public static void init(IHttpProcessor httpProcessor) {
        iHttpProcessor = httpProcessor;
    }


    // 并不是自己使用，而是交给代理类做事
    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {
        // 拼接参数
        final String finalUrl = appendParams(url,params);
        iHttpProcessor.post(finalUrl,params,callback);
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {
        // 拼接参数 省代码
        final String finalUrl = appendParams(url,params);
        iHttpProcessor.get(finalUrl,params,callback);
    }

    // URI 不允许有空格等字符，如果参数值有空格，需要此方法替换
    private static String encode(String str){
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // 针对 不支持的编码时报错，utf-8 应该是支持的
            Log.e("参数转码异常",e.toString());
            throw new RuntimeException(e);
        }
    }
    private static String appendParams(String url, Map<String,Object> params) {
        if (params == null || params.isEmpty()){
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (urlBuilder.indexOf("?")<0){
            urlBuilder.append("?");
        } else {
            if (!urlBuilder.toString().endsWith("?")){
                urlBuilder.append("&");
            }
        }

        for (Map.Entry<String, Object> entity :
                params.entrySet()) {
            urlBuilder.append(entity.getKey()).append("=").append(encode(entity.getValue().toString()));
        }
        return urlBuilder.toString();
    }
}

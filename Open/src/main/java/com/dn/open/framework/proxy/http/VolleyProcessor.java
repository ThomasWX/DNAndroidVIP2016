package com.dn.open.framework.proxy.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * 相当于第四步
 */
public class VolleyProcessor implements IHttpProcessor{
    /*
    Volley使用三部曲：
    1，初始化Volley,初始化一个队列
    2, 初始化一个StringRequest
    3, 往队列中添加请求
     */
    private static RequestQueue requestQueue = null;

    public VolleyProcessor(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void get(String url, Map<String, Object> params, final ICallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}

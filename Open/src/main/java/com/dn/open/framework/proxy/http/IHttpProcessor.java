package com.dn.open.framework.proxy.http;

import java.util.Map;

/**
 * 代理模式的开端，接口规范类
 * 相当于“框架搭建重构”第二步
 */
public interface IHttpProcessor {
    // 网络访问：Post,get,Del,update，put
    void post(String url, Map<String, Object> params, ICallback callback);

    void get(String url, Map<String, Object> params, ICallback callback);
}

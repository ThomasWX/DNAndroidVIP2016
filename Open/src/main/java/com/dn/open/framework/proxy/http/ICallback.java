package com.dn.open.framework.proxy.http;

/**
 * 网络访问成功与失败的接口
 */
public interface ICallback {
    void onSuccess(String result);
    void onFailure(String error);
}

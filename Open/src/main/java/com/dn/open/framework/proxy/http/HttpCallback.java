package com.dn.open.framework.proxy.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 网络返回结果的基础类
 * @param <Result>
 */
public abstract class HttpCallback<Result> implements ICallback {
    @Override
    public void onSuccess(String result) {
        // 将String(网络层的字符流)转换成Result(即泛型)
        Gson gson = new Gson();
        Class<?> clz = analysisClass(this);
        // 使用反射
        Result objResult = (Result) gson.fromJson(result,clz);// clz是为了json解析用的
        onSuccess(objResult);
    }

    protected abstract void onSuccess(Result objResult);


    protected  static Class<?> analysisClass(Object object){
        Type genType = object.getClass().getGenericSuperclass();// 获取object的所有超类
        // 从所有超类中找到Result类的名字
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();// 强转为参数化的Type
        return (Class<?>) params[0]; // 这个0 就是Result这个泛型代表的类
    }
}

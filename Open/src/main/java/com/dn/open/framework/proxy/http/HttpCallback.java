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

    /*
    analysisClass 分析：
    通过传递过来的object (即this ,HttpCallback对象)，通过反射将object里面所有上下文中使用的类的名字全部反射出来->Type ，
    然后强转成Type[] (Type是Java中高级类的名字),最后转换成一个Class.

    疑问：可不可以在ICallback中直接用一个泛型？
    答：不能，因为网络返回的数据是String的，而我们定义接口的时候经常用泛型是可行的，是因为我们没有去拿第一手的网络返回String数据.
     */
}

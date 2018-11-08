package com.dn.open.permission.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//该注解执行在方法之上
@Retention(RetentionPolicy.RUNTIME)//虚拟机在运行时通过反射来获取注解的值
public @interface IPermission {
    int value();
}

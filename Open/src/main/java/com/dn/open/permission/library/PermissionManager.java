package com.dn.open.permission.library;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.dn.open.permission.BaseActivity;
import com.dn.open.permission.PermissionActivity;
import com.dn.open.permission.library.annotation.IPermission;
import com.dn.open.permission.library.helper.PermissionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
    /**
     * 检查权限是否被授予
     *
     * @param activity
     * @param perms
     * @return
     */
    public static boolean hasPermissions(@NonNull Activity activity, @NonNull String... perms) {
        // 如果低于6.0版本无需做运行时权限判断
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm :
                perms) {
            // 如果循环出来任意一个权限没有被授权则返回false
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(@NonNull Activity activity, int requestCode, @NonNull String... perms) {
        // 发起权限请求之前，检查一下权限状态
        if (hasPermissions(activity, perms)) { // 全部通过
            allHasPermission(activity, requestCode, perms);
            return;
        }
        // 权限请求
        PermissionHelper helper = PermissionHelper.newInstance(activity);
        helper.requestPermissions(requestCode, perms);

    }

    private static void allHasPermission(Activity activity, int requestCode, String[] perms) {
        int[] grantResults = new int[perms.length];
        for (int i = 0; i < grantResults.length; i++) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED;
        }
        onRequestPermissionsResult(requestCode, perms, grantResults, activity);
    }

    /**
     * 处理权限请求结果
     * 如果授予或者拒绝任何权限，将通过PermissionCallback回调接受结果
     * 以及运行有@IPermission注解的方法
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 授权结果
     * @param activity     拥有实现PermissionCallback接口或者有@IPermission注解的Activity
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Activity activity) {
        List<String> granted = new ArrayList<>();// 允许
        List<String> denied = new ArrayList<>(); // 拒绝
        // 分类
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) { // 通过
                granted.add(permissions[i]);
            } else {
                denied.add(permissions[i]);
            }
        }
        // 开始回调
        if (!granted.isEmpty()) {
            if (activity instanceof PermissionCallback) {
                ((PermissionCallback) activity).onPermissionGranted(requestCode, granted);
            }
        }

        if (!denied.isEmpty()) {
            if (activity instanceof PermissionCallback) {
                ((PermissionCallback) activity).onPermissionDenied(requestCode, denied);
            }
        }


        // 可以可可无，如果权限都通过，才执行注解方法，哪怕多个权限中一个被拒绝也不执行方法
        if (!granted.isEmpty() && denied.isEmpty()) {
            reflectAnnotationMethod(activity, requestCode);
        }

    }

    /**
     * 找到指定Activity中，有Ipermission注解的，并且请求标识码参数正确的 方法
     * <p>
     * 目的：再次调用相应的方法！
     */
    private static void reflectAnnotationMethod(Activity activity, int requestCode) {
        // 拿到类
        Class<? extends Activity> clazz = activity.getClass();
        // 拿到此类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历所有方法
        for (Method method :
                methods) {
            // 如果方法是IPermission注解
            if (method.isAnnotationPresent(IPermission.class)) { // 第一层校验
                // 获取注解
                IPermission iPermission = method.getAnnotation(IPermission.class);
                // 如果注解的值等于请求标识码(两次匹配，避免框架(第三方的)冲突)
                if (iPermission.value() == requestCode) { // 第二层校验
                    // 严格控制方法格式和规范
                    // 方法返回必须是void（第三层校验）
                    Type returnType = method.getGenericReturnType();
                    if (!"void".equals(returnType.toString())) {
                        throw new RuntimeException(method.getName() + "方法返回必须是void");
                    }
                    // 方法参数必须无参(第四层校验)
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length > 0) {
                        throw new RuntimeException(method.getName() + "方法必须是无参的");
                    }

                    try {
                        //注意： 如果目标方法是 private， 则强制设置其允许访问
                        if (!method.isAccessible()) method.setAccessible(true);
                        method.invoke(activity);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }

    public static boolean hasDeniedForever(Activity activity, List<String> perms) {
        return PermissionHelper.newInstance(activity).hasDeniedForever(perms);
    }
}

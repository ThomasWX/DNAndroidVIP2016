package com.dn.open.permission.library;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.dn.open.permission.BaseActivity;
import com.dn.open.permission.PermissionActivity;
import com.dn.open.permission.library.helper.PermissionHelper;

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

    }

    public static boolean hasDeniedForever(Activity activity, List<String> perms) {
        return PermissionHelper.newInstance(activity).hasDeniedForever(perms);
    }
}

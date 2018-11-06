package com.dn.open.permission.library;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.dn.open.permission.library.helper.PermissionHelper;

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
            allHasPermission();
            return;
        }
        // 权限请求
        PermissionHelper helper = PermissionHelper.newInstance(activity);
        helper.requestPermissions(requestCode, perms);

    }

    private static void allHasPermission() {
    }
}

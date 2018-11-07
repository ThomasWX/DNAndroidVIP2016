package com.dn.open.permission.library.helper;

import android.app.Activity;
import android.os.Build;

/**
 * 抽象辅助类
 */
public abstract class PermissionHelper {
    private Activity activity;

    public PermissionHelper(Activity activity){
        this.activity = activity;
    }

    public Activity getHost(){
        return activity;
    }

    public static PermissionHelper newInstance(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            new LowApiPermissionHelper(activity);
        }
        return new ActivityPermissionHelper(activity);
    }

    public abstract void requestPermissions(int requestCode, String[] perms) ;
}

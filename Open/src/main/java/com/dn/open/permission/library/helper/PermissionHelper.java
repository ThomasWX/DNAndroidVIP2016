package com.dn.open.permission.library.helper;

import android.app.Activity;

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
        return null;
    }

    public abstract void requestPermissions(int requestCode, String[] perms) ;
}

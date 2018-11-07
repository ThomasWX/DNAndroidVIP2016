package com.dn.open.permission.library.helper;

import android.app.Activity;

/**
 * 低版本 <6.0 申请权限辅助类
 */
class LowApiPermissionHelper extends PermissionHelper{
    LowApiPermissionHelper(Activity activity) {
        super(activity);
    }

    @Override
    public void requestPermissions(int requestCode, String[] perms) {
        throw new IllegalStateException("低于6.0版本无需运行时权限申请");
    }

    @Override
    protected boolean shouldShowRequestPermissionRationale(String deniedPermission) {
        return false;
    }
}

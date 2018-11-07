package com.dn.open.permission.library.helper;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

class ActivityPermissionHelper extends PermissionHelper {
    public ActivityPermissionHelper(Activity activity) {
        super(activity);
    }

    @Override
    public void requestPermissions(int requestCode, String[] perms) {
        ActivityCompat.requestPermissions(getHost(), perms, requestCode);
    }
}

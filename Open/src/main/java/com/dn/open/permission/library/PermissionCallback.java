package com.dn.open.permission.library;

import java.util.List;

/**
 * 权限回调监听
 */
public interface PermissionCallback {
    void onPermissionGranted(int requestCode, List<String> perms);

    void onPermissionDenied(int requestCode, List<String> perms);
}

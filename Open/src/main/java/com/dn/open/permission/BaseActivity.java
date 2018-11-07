package com.dn.open.permission;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.dn.open.permission.library.PermissionCallback;
import com.dn.open.permission.library.PermissionManager;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements PermissionCallback {
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {

    }
}

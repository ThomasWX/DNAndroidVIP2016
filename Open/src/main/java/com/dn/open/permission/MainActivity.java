package com.dn.open.permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dn.open.R;
import com.dn.open.permission.library.PermissionManager;

public class MainActivity extends PermissionActivity {

    private static final int CAMERA_REQUEST_CODE = 11;//拍照权限请求码
    private static final int LOCATION_CONTACT__REQUEST_CODE = 22;//拍照权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }



    // 点击事件
    public void applySingle(View view) {
        applySinglePermission();
    }

    public void applyMultiple(View view) {
        applyMultiplePermissions();
    }


    public void applySinglePermission() {
        if (PermissionManager.hasPermissions(this,Manifest.permission.CAMERA)) {//授权通过
            Toast.makeText(this, "授权通过", Toast.LENGTH_SHORT).show();
        } else {
            PermissionManager.requestPermissions(this, CAMERA_REQUEST_CODE, Manifest.permission.CAMERA);
        }

    }

    public void applyMultiplePermissions() {
        String[] requestPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};
        if (PermissionManager.hasPermissions(this,requestPermissions)) {//授权通过
            Toast.makeText(this, "全部授权通过", Toast.LENGTH_SHORT).show();
        } else {
            PermissionManager.requestPermissions(this, LOCATION_CONTACT__REQUEST_CODE, requestPermissions);
        }
    }




}

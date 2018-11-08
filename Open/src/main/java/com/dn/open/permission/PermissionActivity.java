package com.dn.open.permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.dn.open.permission.library.dialog.AppSettingDialog;
import com.dn.open.permission.library.PermissionManager;

import java.util.List;

public class PermissionActivity extends BaseActivity {
    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        // 检查用户是否拒绝过某权限，并点击了"不再询问"
        if (PermissionManager.hasDeniedForever(this, perms)) {// 永久拒绝
            // 显示一个对话框，引导用户开启设置中的权限
             new AppSettingDialog.Builder(this).setListener(new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             }).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingDialog.SETTING_CODE){

        }
    }
}

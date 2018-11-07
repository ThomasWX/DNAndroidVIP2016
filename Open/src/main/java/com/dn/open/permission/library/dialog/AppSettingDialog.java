package com.dn.open.permission.library.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;

public class AppSettingDialog {
    public static final int SETTING_CODE = 333; // 跳转设置监听回调标识码

    private String title; // 对话框标题
    private String message; // 解释为什么需要这组权限的提示内容
    private String positiveButton;// 确定按钮
    private String negativeButton; // 取消按钮
    private DialogInterface.OnClickListener listener; // 对话框点击监听
    private int requestCode = -1; //请求标识码

    public AppSettingDialog(Builder builder) {
        // TODO 1min18s
    }

    // 构造者模式
    public static class Builder {
        private Activity activity;
        private String title; // 对话框标题
        private String message; // 解释为什么需要这组权限的提示内容
        private String positiveButton;// 确定按钮
        private String negativeButton; // 取消按钮
        private DialogInterface.OnClickListener listener; // 对话框点击监听
        private int requestCode = -1; //请求标识码

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setTitle(String title) {
            this.title = title;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(String positiveButton) {
            this.positiveButton = positiveButton;
            return this;
        }

        public Builder setNegativeButton(String negativeButton) {
            this.negativeButton = negativeButton;
            return this;
        }

        public Builder setListener(DialogInterface.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public void setRequestCode(int requestCode) {
            this.requestCode = requestCode;
        }

        // 构造者的检测
        public AppSettingDialog build() {
            this.title = "需要的权限";
            this.message = TextUtils.isEmpty(message) ? "打开设置，启动权限" : message;
            this.positiveButton = activity.getString(android.R.string.ok);
            this.negativeButton = activity.getString(android.R.string.cancel);
            this.requestCode = requestCode > 0 ? requestCode : SETTING_CODE;
            return new AppSettingDialog(this);
        }
    }


}

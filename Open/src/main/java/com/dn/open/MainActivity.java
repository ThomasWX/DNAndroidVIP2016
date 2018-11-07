package com.dn.open;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dn.open.permission.PermissionCheckActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void checkRuntimePermission(View view) {
         startActivity(new Intent(this, PermissionCheckActivity.class));
    }

}

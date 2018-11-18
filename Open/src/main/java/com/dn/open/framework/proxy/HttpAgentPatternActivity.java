package com.dn.open.framework.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dn.open.R;
import com.dn.open.framework.proxy.http.HttpProxy;
import com.dn.open.framework.proxy.http.VolleyProcessor;

public class HttpAgentPatternActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化代理，一般在Application onCreate 中设置，此处写在这里
        HttpProxy.init(new VolleyProcessor(this));
        setContentView(R.layout.activity_http_proxy);
    }
}

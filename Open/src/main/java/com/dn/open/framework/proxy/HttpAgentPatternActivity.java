package com.dn.open.framework.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dn.open.R;
import com.dn.open.framework.proxy.bean.PhotoSetInfo;
import com.dn.open.framework.proxy.http.HttpCallback;
import com.dn.open.framework.proxy.http.HttpProxy;
import com.dn.open.framework.proxy.http.OkHttpProcessor;
import com.dn.open.framework.proxy.http.VolleyProcessor;

import java.util.HashMap;
import java.util.Map;

public class HttpAgentPatternActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = HttpAgentPatternActivity.class.getSimpleName();
    private String url = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";// 网易新闻中天气
    private Map<String,Object> params = new HashMap<>();
    TextView textView;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化代理，一般在Application onCreate 中设置，此处写在这里
//        HttpProxy.init(new VolleyProcessor(this));
        HttpProxy.init(new OkHttpProcessor() );
        //        HttpProxy.init(new XUtilsProcessor(this));
        // Retrofit 该怎么改？ 需和RxJava共同使用，用两者编写RetrofitProcessor后，implement IHttpProcessor , 注：Retrofit 是将get\post 请求进行二次封装。





        setContentView(R.layout.activity_http_proxy);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.bttton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HttpProxy.obtain().post(url, params, new HttpCallback<PhotoSetInfo>() {
            @Override
            public void onFailure(String error) {
                Toast.makeText(HttpAgentPatternActivity.this, "网络失败！", Toast.LENGTH_SHORT).show();
                Log.e(TAG,error);
            }

            @Override
            protected void onSuccess(PhotoSetInfo objResult) {
                // 如何将网络中的一个String 转换成 PhotoSetInfo，见HttpCallback的反射
                StringBuilder sb = new StringBuilder();
                if (objResult != null){
                    sb.append("来源： ")
                            .append(objResult.getSource())
                            .append("\r\n")
                            .append("Tag:")
                            .append(objResult.getSettag())
                            .append("\r\n")
                            .append("天气描述：")
                            .append(objResult.getDesc());

                }
                textView.setText(sb.toString());

            }
        });// Callback 用HttpCallback,而不是用ICallback, 原因是  要用泛型  解析各种Json Bean
    }
}

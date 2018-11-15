package com.dn.ui.md.recycler.call;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dn.ui.R;
import com.dn.ui.md.recycler.l3.WrapRecyclerView;

public class CallLogDetailActivity extends AppCompatActivity implements View.OnClickListener , CallLogQueryHandler.Listener{
    private static final int CALL_LOG_PERMISSION_CODE = 1010;
    private WrapRecyclerView mRecyclerView;
    private CallLogDetailAdapter adapter;

    private CallLogQueryHandler queryHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_detial);

        checkPerms(Manifest.permission.READ_CALL_LOG);




        mRecyclerView = findViewById(R.id.call_log_detail_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 注意：inflate时必须将ViewGroup root -> mRecyclerView 设置上去，不然header/footer会不居中显示。
        View header = LayoutInflater.from(this).inflate(R.layout.call_log_detail_header, mRecyclerView, false);
        View footer = LayoutInflater.from(this).inflate(R.layout.call_log_detail_footer, mRecyclerView, false);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.addFooterView(footer);

        String number = "13479790065";
        String name = "凯莎迷";

        queryHandler = new CallLogQueryHandler(getContentResolver(), this);
        queryHandler.queryCallLogs(number);

        TextView tvName = header.findViewById(R.id.call_log_name);
        tvName.setText(name);
        ImageButton dial = footer.findViewById(R.id.imageButton_dial);
        dial.setOnClickListener(this);
        adapter = new CallLogDetailAdapter(this, number);
        mRecyclerView.setAdapter(adapter);
        // adapter.printHashCode();
    }

    private void checkPerms(String... permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, CALL_LOG_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_LOG_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限授予成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        adapter.clear();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // 拨号
        Toast.makeText(this, "正在拨号，请稍后!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (cursor == null) return;
        if (token == CallLogQueryHandler.QUERY_CALL_LOG_TOKEN) {
            /*
            if (cursor == mCursor) return;
            if (mCursor != null) {
                mCursor.close();
            }
            mCursor = cursor;
            if (mCursor != null) notifyDataSetChanged();
            */
            adapter.parse(cursor);
            mRecyclerView.setAdapter(adapter);
            mHandler.sendEmptyMessageDelayed(-1,3000);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.test();
        }
    };

}

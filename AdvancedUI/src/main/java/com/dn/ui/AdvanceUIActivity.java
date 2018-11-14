package com.dn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dn.ui.md.recycler.RecyclerViewActivity;
import com.dn.ui.md.recycler.call.CallLogDetailActivity;

public class AdvanceUIActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private  SparseIntArray mArray;
    private ArrayMap<String, Integer> mMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void test() {
        ListView mListView = null;
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void jumpDNCourse(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }

    public void jumpNubia(View view) {
        Intent intent = new Intent(this, CallLogDetailActivity.class);
        startActivity(intent);
    }

    /*数组相关知识点*/
    /*
    ArrayList与LinkedList
    1,




    大数据遍历操作：ArrayList 宜用 for循环 , LinkedList宜用迭代器; forEach两者都不讨好
     */

}

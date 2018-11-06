package com.dn.ui.md.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.ui.R;
import com.dn.ui.md.recycler.l1.RecyclerViewAdapter;
import com.dn.ui.md.recycler.l1.WaterfallsRecyclerAdapter;
import com.dn.ui.md.recycler.l2.DividerGridViewItemDecoration;
import com.dn.ui.md.recycler.l2.SimpleItemDecoration;
import com.dn.ui.md.recycler.l3.WrapRecyclerView;
import com.dn.ui.md.recycler.l4.Data;
import com.dn.ui.md.recycler.l4.ItemTouchAdapter;
import com.dn.ui.md.recycler.l4.Message;
import com.dn.ui.md.recycler.l4.RecyclerItemTouchCallback;
import com.dn.ui.md.recycler.l5.SkidActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private WrapRecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private boolean isGridLayout = false;// 是否为网格布局
    private boolean isVerticalDirection;// 线性布局方向 - 垂直
    private boolean isDividingLineShown = false;
    private boolean isHeaderOrFooterShown = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        initView();
//        simplePackage();
//        animation();
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < 25; i++) {
//            list.add(String.valueOf(i));
//        }
//        adapter = new RecyclerViewAdapter(list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);


        // lesson 3 add HeaderView and FooterView



        // 布局摆放管理器(线性、瀑布流)
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        decor = new SimpleItemDecoration(this, LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(decor);


        // 数据倒置 从右边开始滑动
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        // 瀑布流效果
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL));


    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        horizontalManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        verticalManager = new LinearLayoutManager(this); // 默认垂直

//        horizontalItemDecoration = new SimpleItemDecoration(this, LinearLayoutManager.HORIZONTAL);
//        verticalItemDecoration = new SimpleItemDecoration(this, LinearLayoutManager.VERTICAL);
        horizontalItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.HORIZONTAL);
        verticalItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);

        initHeaderViewsAndFooterViews();
    }

    // ----------------点击事件--------------//
    public void switchLinearDirection(View view) {
        isGridLayout = false;
        if (isVerticalDirection) { // 切到水平方向
            recyclerView.setLayoutManager(horizontalManager);
        } else {// 切到垂直方向
            recyclerView.setLayoutManager(verticalManager);
        }
        isVerticalDirection = !isVerticalDirection;
        if (isDividingLineShown) {
            dividingLine();
        }
    }

    public void switchLayout(View view) {
        if (isGridLayout) { // 切回线性
            recyclerView.setLayoutManager(verticalManager);
            isVerticalDirection = true;
        } else { // 切到网格
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        isGridLayout = !isGridLayout;
        if (isDividingLineShown) {
            dividingLine();
        }
    }

    public void addSpacer(View view){
        dividingLine();
    }

    public void addHeaderAndFooter(View view){
        recyclerView.addHeaderView(headerView);
        recyclerView.addFooterView(footerView);
        isHeaderOrFooterShown = true;
    }

    public void skid(View view){
        startActivity(new Intent(this,SkidActivity.class));
    }

    // ----------------点击事件--------------//



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                if (isDividingLineShown && decor != null){
                    recyclerView.removeItemDecoration(decor);
                    isDividingLineShown = false;
                }
                if (isHeaderOrFooterShown){
                    recyclerView.removeHeaderView(headerView);
                    recyclerView.removeFooterView(footerView);
                    adapter.notifyDataSetChanged();
                    isHeaderOrFooterShown = false;
                }
                break;
            case R.id.menu_add_item:
                adapter.addData(3);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //--------------------------------------------------------------------------------------------//
    private LinearLayoutManager horizontalManager, verticalManager;

    /**
     * Simple package 简单封装
     */
    private void simplePackage() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            list.add(String.valueOf(i));
        }

        isVerticalDirection = true;
        recyclerView.setLayoutManager(verticalManager);




        adapter = new RecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);


        WaterfallsRecyclerAdapter waterfallsRecyclerAdapter = new WaterfallsRecyclerAdapter(list);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });


    }

    //--------------------------------------------------------------------------------------------//
    private ItemDecoration decor;

    private ItemDecoration horizontalItemDecoration, verticalItemDecoration;


    /**
     * Spacer Line 间隔线
     */
    private void dividingLine() {
        if (decor != null) {
            recyclerView.removeItemDecoration(decor);
        }
        decor = isVerticalDirection ? verticalItemDecoration : horizontalItemDecoration;
        if (isGridLayout) {
            decor = new DividerGridViewItemDecoration(this);
        }
        recyclerView.addItemDecoration(decor);
        isDividingLineShown = true;
    }




    //--------------------------------------------------------------------------------------------//

    /**
     * Add HeaderView FooterView 添加头部和底部视图
     */
    private ImageView headerView;
    private TextView footerView;

    private void initHeaderViewsAndFooterViews(){
        headerView = new ImageView(this);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerView.setImageResource(R.drawable.ic_launcher_background);

        footerView = new TextView(this);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        footerView.setText("底部布局视图");
        footerView.setTextSize(14);
    }
    //--------------------------------------------------------------------------------------------//

    ItemTouchHelper itemTouchHelper;

    /**
     * RecyclerView交互动画
     */
    private void animation() {
        recyclerView.setLayoutManager(verticalManager);
        List<Message> data = Data.init();


        ItemTouchAdapter.StartDragListener listener = new ItemTouchAdapter.StartDragListener() {
            @Override
            public void onStartDrag(ItemTouchAdapter.ViewHolder holder) {
                if (itemTouchHelper != null)
                    itemTouchHelper.startDrag(holder);
            }
        };


        ItemTouchAdapter adapter = new ItemTouchAdapter(data, listener);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new RecyclerItemTouchCallback(adapter);
        // RecyclerView触摸帮助类，用来监听RecyclerView中的条目触摸事件，但是条目的动画需要自己实现。
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * 侧滑
     */


}

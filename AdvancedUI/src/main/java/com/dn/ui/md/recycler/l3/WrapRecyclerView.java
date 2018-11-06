package com.dn.ui.md.recycler.l3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

public class WrapRecyclerView extends RecyclerView {
    private static final String TAG = WrapRecyclerView.class.getSimpleName();

    public static final int HEADER_TYPE = 10001;
    public static final int FOOTER_TYPE = 10002;

    /**
     * 通过XML来实现，所以使用中间这个构造方法
     */
    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 写代码的最高境界：复制粘贴
     * 复制ListView的源码
     */


    ArrayList<View> mHeaderViews = new ArrayList<>();
    ArrayList<View> mFooterViews = new ArrayList<>();

    RecyclerView.Adapter mAdapter;


    @Override
    public void setAdapter(Adapter adapter) {

        if (mHeaderViews.size() > 0 || mFooterViews.size() > 0) {
            mAdapter = wrapHeaderListAdapterInternal(mHeaderViews, mFooterViews, adapter);
        } else {
            mAdapter = adapter;
        }

        super.setAdapter(mAdapter);
    }

    public void addHeaderView(View v) {
        mHeaderViews.add(v);
        // Wrap the adapter if it wasn't already wrapped. 
        if (mAdapter != null) {
            if (!(mAdapter instanceof HeaderViewRecyclerAdapter)) {
                wrapHeaderListAdapterInternal();
            }
        }
    }

    public void addFooterView(View v) {
        mFooterViews.add(v);
        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof HeaderViewRecyclerAdapter)) {
                wrapHeaderListAdapterInternal();
            }
        }
    }

    /**
     * 参考 ListView removeHeaderView
     */
    public boolean removeHeaderView(View v) {
        if (mHeaderViews.size() > 0) {
            boolean result = false;
            if (mAdapter != null && ((HeaderViewRecyclerAdapter) mAdapter).removeHeader(v)) {
                result = true;
            }
            removeFixedViewInfo(v, mHeaderViews);
            return result;
        }
        return false;
    }


    public boolean removeFooterView(View v) {
        if (mFooterViews.size() > 0) {
            boolean result = false;
            if (mAdapter != null && ((HeaderViewRecyclerAdapter) mAdapter).removeFooter(v)) {
                result = true;
            }
            removeFixedViewInfo(v, mFooterViews);
            return result;
        }
        return false;
    }



    private void removeFixedViewInfo(View v, ArrayList<View> where) {
        int len = where.size();
        for (int i = 0; i < len; ++i) {
            View view = where.get(i);
            if (view == v) {
                where.remove(i);
                break;
            }
        }
    }


    protected HeaderViewRecyclerAdapter wrapHeaderListAdapterInternal(
            ArrayList<View> headerViews,
            ArrayList<View> footerViews,
            RecyclerView.Adapter adapter) {
        return new HeaderViewRecyclerAdapter(headerViews, footerViews, adapter);
    }


    protected void wrapHeaderListAdapterInternal() {
        mAdapter = wrapHeaderListAdapterInternal(mHeaderViews, mFooterViews, mAdapter);
    }

}

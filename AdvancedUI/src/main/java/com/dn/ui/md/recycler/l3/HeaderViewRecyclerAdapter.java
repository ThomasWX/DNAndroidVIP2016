package com.dn.ui.md.recycler.l3;

import android.support.v7.widget.RecyclerView;


import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 参考 HeaderViewListAdapter.java
 */
class HeaderViewRecyclerAdapter extends RecyclerView.Adapter {
    private final RecyclerView.Adapter mAdapter;
    // These two ArrayList are assumed to NOT be null.
    // They are indeed created when declared in ListView and then shared.
    ArrayList<View> mHeaderViews;
    ArrayList<View> mFooterViews;


    // Used as a placeholder in case the provided info views are indeed null.
    // Currently only used by some CTS tests, which may be removed.
    static final ArrayList<View> EMPTY_INFO_LIST =
            new ArrayList<View>();


    public HeaderViewRecyclerAdapter(ArrayList<View> headerViews, ArrayList<View> footerViews, RecyclerView.Adapter adapter) {
        mAdapter = adapter;

        if (headerViews == null) {
            mHeaderViews = EMPTY_INFO_LIST;
        } else {
            mHeaderViews = headerViews;
        }

        if (footerViews == null) {
            mFooterViews = EMPTY_INFO_LIST;
        } else {
            mFooterViews = footerViews;
        }

    }


    @Override
    public int getItemViewType(int position) {

        int numHeaders = getHeadersCount();

        // 判断当前条目是什么类型的---决定渲染什么视图给什么数据
        // Header
        if (position < numHeaders) {
            return WrapRecyclerView.HEADER_TYPE;
        }

        // Body
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }

        }

        // Footer
        return WrapRecyclerView.FOOTER_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Header,Footer 不必使用ViewHolder机制
        if (viewType == WrapRecyclerView.HEADER_TYPE) {
            return new ViewHolder(mHeaderViews.get(0));
        } else if (viewType == WrapRecyclerView.FOOTER_TYPE) {
            return new ViewHolder(mFooterViews.get(0));
        }


        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Header (negative positions will throw an IndexOutOfBoundsException)
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return ;
        }


        // Adapter
        final int adjPosition = position - numHeaders;
        if (mAdapter != null) {
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder,adjPosition);
                return;
            }
        }



        // Footer (off-limits positions will throw an IndexOutOfBoundsException)

    }

    @Override
    public int getItemCount() {

        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }


    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    public boolean removeHeader(View v) {
        for (int i = 0; i < mHeaderViews.size(); i++) {
            View view = mHeaderViews.get(i);
            if (view == v) {
                mHeaderViews.remove(i);
                return true;
            }
        }

        return false;
    }


    public boolean removeFooter(View v) {
        for (int i = 0; i < mFooterViews.size(); i++) {
            View view = mFooterViews.get(i);
            if (view == v) {
                mFooterViews.remove(i);
                return true;
            }
        }

        return false;
    }


    private static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}

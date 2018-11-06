package com.dn.ui.md.recycler.l2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

public class DividerGridViewItemDecoration extends ItemDecoration {

    private Drawable mDivider;

    private int[] attrs = new int[]{android.R.attr.listDivider};


    public DividerGridViewItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    // 绘制垂直间隔线
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        View child = null;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();

            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int bottom = child.getBottom() + params.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

        }


    }

    // 绘制水平间隔线
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        View child = null;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (LayoutParams) child.getLayoutParams();

            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        // 获取四个方向的偏移量

        /*
        // 保证最右边和最下边不画线  - - - - 个人觉得isLastRow\isLastColumn outRect.set(0, 0, 0, 0);即可
        if (isLastRow())
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        else if (isLastColumn())
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        else
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());

        */
        // 注意，如果是最后一列和最后一行，如上两个if 都不会进

        // 改进：
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
        if (isLastRow(parent, itemPosition))
            bottom = 0;
        if (isLastColumn(parent, itemPosition))
            right = 0;

        outRect.set(0, 0, right, bottom);

    }

    /**
     * 是否为最后一行
     *
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int position) {
        LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = getSpanCount(parent);
        if (layoutManager instanceof GridLayoutManager) {
            int childCount = parent.getAdapter().getItemCount();
            // 最后一行的数量小于spanCount
            int lastRowCount = childCount % spanCount;

            if (lastRowCount == 0 || lastRowCount < spanCount) {
                return true;
            }
        }
        return false;

    }

    /**
     * 是否为最后一列
     *
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int position) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (position + 1) % getSpanCount(parent) == 0;
        }
        return  false;
    }

    /**
     * 代表有多少列
     */
    private int getSpanCount(RecyclerView parent) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;

            return manager.getSpanCount();
        }
        return 0;
    }
}

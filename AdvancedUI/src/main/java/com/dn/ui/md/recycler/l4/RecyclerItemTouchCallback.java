package com.dn.ui.md.recycler.l4;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import com.dn.ui.R;

public class RecyclerItemTouchCallback extends Callback {

    public RecyclerItemTouchCallback(ItemTouchMoveListener listener) {
        moveListener = listener;
    }


    public interface ItemTouchMoveListener {
        /**
         * 当拖拽的时候回调
         * 可以实现：拖拽条目并且刷新的效果
         *
         * @param fromPosition 起点
         * @param toPosition   终点
         * @return 是否执行了move
         */
        boolean onItemMove(int fromPosition, int toPosition);

        /**
         * 当条目被移除的时候回调
         *
         * @param position 移除的位置
         * @return
         */
        boolean onItemRemove(int position);
    }


    private ItemTouchMoveListener moveListener;

    /**
     * Callback回调监听时先调用的，用来判断当前是什么动作，比如判断方向。
     * 即 监听哪个方向的拖动。
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 方向：up,down,left,right
        // 常量：
        int up = ItemTouchHelper.UP; //1  0x0001
        int down = ItemTouchHelper.DOWN;//2  0x0010
        int left = ItemTouchHelper.LEFT;
        int right = ItemTouchHelper.DOWN;

        // 我要监听的拖拽方向是哪个方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // 监听侧滑的方向是哪个方向
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int flag = makeMovementFlags(dragFlags, swipeFlags);
        return flag;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 是否允许长按拖拽效果
        return true;
    }

    /**
     * 当移动的时候回调，拖拽
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // 在拖拽的过程中不断调用adater.notifyItemMoved(from,to);
        return moveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    /**
     * 当侧滑的时候回调
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 监听侧滑，1.删除数据；2.调用adapter.notifyItemRemove(position)
        moveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // 判断选中状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            Context context = viewHolder.itemView.getContext();
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary, context.getTheme()));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 恢复
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // dX：水平方向移到的增量(负数：往左，正数：往右) 范围：0-View.getWidth  0--1
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            // 透明度动画
            float alpha = 1 - Math.abs(dX)/viewHolder.itemView.getWidth();// Math.abs 取绝对值
            viewHolder.itemView.setAlpha(alpha); // 1--0
            viewHolder.itemView.setScaleX(alpha);// 1--0
            viewHolder.itemView.setScaleY(alpha);// 1--0
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}

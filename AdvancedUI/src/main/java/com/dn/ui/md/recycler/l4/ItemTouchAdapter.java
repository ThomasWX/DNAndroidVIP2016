package com.dn.ui.md.recycler.l4;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.ui.R;

import java.util.Collections;
import java.util.List;

public class ItemTouchAdapter extends RecyclerView.Adapter<ItemTouchAdapter.ViewHolder> implements RecyclerItemTouchCallback.ItemTouchMoveListener {

    public interface StartDragListener {
        /**
         * 用于需要主动回调拖拽效果的地方
         *
         * @param holder
         */
        void onStartDrag(ViewHolder holder);
    }

    private List<Message> list;
    private StartDragListener dragListener;

    public ItemTouchAdapter(List<Message> list, StartDragListener dragListener) {
        this.list = list;
        this.dragListener = dragListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_touch, parent, false);
        return new ViewHolder(view, dragListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Message msg = list.get(position);
        holder.iv_logo.setImageResource(msg.getLogo());
        holder.tv_name.setText(msg.getName());
        holder.tv_msg.setText(msg.getLastMsg());
        holder.tv_time.setText(msg.getTime());

        // 实现触摸logo头像即可拖拽，等效于长按拖拽 功能
//        holder.iv_logo.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    dragListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_logo;
        TextView tv_name;
        TextView tv_msg;
        TextView tv_time;
        StartDragListener dragListener;

        public ViewHolder(View itemView, final StartDragListener listener) {
            super(itemView);

            // find by id
            iv_logo = itemView.findViewById(R.id.iv_logo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            tv_time = itemView.findViewById(R.id.tv_time);

            // 实现触摸logo头像即可拖拽，等效于长按拖拽 功能
            dragListener = listener;
            iv_logo.setOnTouchListener(touchListener);
        }

        View.OnTouchListener touchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(ViewHolder.this);
                }
                return false;
            }
        };

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // 数据交互；刷新
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        return true;
    }




}

package com.dn.ui.md.recycler.l1;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WaterfallsRecyclerAdapter extends RecyclerView.Adapter<WaterfallsRecyclerAdapter.MyViewHolder> {
    private List<String> list;
    private List<Integer> heights;

    public WaterfallsRecyclerAdapter(List<String> list) {
        this.list = list;
        heights = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            heights.add((int) Math.max(200, Math.random() * 550));
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建 ViewHolder
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 绑定数据
        ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
        params.height = heights.get(position);

        holder.tv.setBackgroundColor(Color.rgb(100, (int) (Math.random() * 255), (int) (Math.random() * 255)));
        holder.tv.setLayoutParams(params);
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }
    }
}

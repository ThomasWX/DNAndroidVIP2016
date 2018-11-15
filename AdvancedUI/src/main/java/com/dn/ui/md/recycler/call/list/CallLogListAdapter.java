package com.dn.ui.md.recycler.call.list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.dn.ui.R;
import com.dn.ui.md.recycler.call.CallLogDetailAdapter;
import com.dn.ui.md.recycler.call.CallLogQuery;
import com.dn.ui.md.recycler.call.CallLogQueryHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogListAdapter extends BaseAdapter implements CallLogQueryHandler.Listener{

    private List<CallLog> mCallLogs ;
    private SimpleDateFormat format = new SimpleDateFormat("MM月 dd日 HH:mm");
    private final CallLogQueryHandler queryHandler;
    public CallLogListAdapter(Context context, String number) {
        this.mCallLogs = new ArrayList<>();
        queryHandler = new CallLogQueryHandler(context.getContentResolver(), this);
        queryHandler.queryCallLogs(number);
    }

    @Override
    public int getCount() {
        return mCallLogs.size();
    }

    @Override
    public Object getItem(int position) {
        return mCallLogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View itemView;
        if (convertView == null){
            holder = new ViewHolder();
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_item,parent,false);
            holder.tvDate = itemView.findViewById(R.id.tv_date);
            holder.tvDuration = itemView.findViewById(R.id.tv_duration);
            holder.tvType = itemView.findViewById(R.id.tv_type);
            holder.ivDivider = itemView.findViewById(R.id.divider);
            convertView = itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            itemView = convertView;
        }

        CallLog call = mCallLogs.get(position);
        if (call != null) {
            if (call.type == android.provider.CallLog.Calls.MISSED_TYPE) {
                holder.tvDuration.setVisibility(View.GONE);
                holder.ivDivider.setVisibility(View.GONE);
                holder.tvType.setTextColor(Color.RED);
            } else {
                holder.tvDuration.setVisibility(View.VISIBLE);
                holder.ivDivider.setVisibility(View.VISIBLE);
                holder.tvType.setTextColor(Color.BLACK);

                holder.tvDate.setText(format.format(new Date(call.date)));

            }

        }

        return itemView;
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        parse(cursor);
    }

    class ViewHolder {
        TextView tvDate, tvDuration, tvType;
        ImageView ivDivider;
    }

    static class CallLog {
        int id;
        long date, duration;
        int type;

        public CallLog(int id, long date, long duration, int type) {
            this.id = id;
            this.date = date;
            this.duration = duration;
            this.type = type;
        }
    }

    private void parse(Cursor cursor) {

//        if (mCallLogs == null){
//            mCallLogs = new ArrayList<>();
//        } else {
//            mCallLogs.clear();
//        }




        CallLog call;
        while (cursor.moveToNext()) {
            call = new CallLog(cursor.getInt(CallLogQuery.ID), cursor.getLong(CallLogQuery.DATE),
                    cursor.getLong(CallLogQuery.DURATION), cursor.getInt(CallLogQuery.TYPE));
            mCallLogs.add(call);
        }




        notifyDataSetChanged();


        Log.d("ListAdapter", "查询完毕，刷新" + cursor.getCount()+"个元素");
        cursor.close();
    }
}

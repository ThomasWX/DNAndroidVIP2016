package com.dn.ui.md.recycler.call;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog.Calls;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.ui.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallLogDetailAdapter extends RecyclerView.Adapter<CallLogDetailAdapter.ViewHolder>  {
    private static final String TAG = "CallLogDetailAdapter";
    private Cursor mCursor;
    private ArrayList<CallLog> mCallLogs ;
    private SimpleDateFormat format = new SimpleDateFormat("MM月 dd日 HH:mm");


    public CallLogDetailAdapter(Context context, String number) {
        mCallLogs = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "[onBindViewHolder] mCallLogs:" + mCallLogs);
        /*
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            int type = mCursor.getInt(CallLogQuery.TYPE);

            if (type == Calls.MISSED_TYPE) {
                holder.tvDuration.setVisibility(View.GONE);
                holder.ivDivider.setVisibility(View.GONE);
                holder.tvType.setTextColor(Color.RED);
            } else {
                holder.tvDuration.setVisibility(View.VISIBLE);
                holder.ivDivider.setVisibility(View.VISIBLE);
                holder.tvType.setTextColor(Color.BLACK);

                holder.tvDate.setText(format.format(new Date(mCursor.getLong(CallLogQuery.DATE))));
                holder.tvDuration.setText(formatDuration(mCursor.getLong(CallLogQuery.DURATION)));
            }
            holder.tvType.setText(formatCallType(type));
        }
        */
        CallLog call = mCallLogs.get(position);
        if (call != null) {
            if (call.type == Calls.MISSED_TYPE) {
                holder.tvDuration.setVisibility(View.GONE);
                holder.ivDivider.setVisibility(View.GONE);
                holder.tvType.setTextColor(Color.RED);
            } else {
                holder.tvDuration.setVisibility(View.VISIBLE);
                holder.ivDivider.setVisibility(View.VISIBLE);
                holder.tvType.setTextColor(Color.BLACK);

                holder.tvDate.setText(format.format(new Date(call.date)));
                holder.tvDuration.setText(formatDuration(call.duration));
            }
            holder.tvType.setText(formatCallType(call.type));
        }

    }

    @Override
    public int getItemCount() {
//        return mCursor == null ? 0 : mCursor.getCount();
        return mCallLogs == null ? 0 : mCallLogs.size();
    }



    public void parse(Cursor cursor) {

//        if (mCallLogs == null){
//            mCallLogs = new ArrayList<>();
//        } else {
//            mCallLogs.clear();
//        }
        mCallLogs.clear();

        printHashCode();

        CallLog call;
        while (cursor.moveToNext()) {
            call = new CallLog(cursor.getInt(CallLogQuery.ID), cursor.getLong(CallLogQuery.DATE),
                    cursor.getLong(CallLogQuery.DURATION), cursor.getInt(CallLogQuery.TYPE));
            mCallLogs.add(call);
        }


        printHashCode();

        Log.d(TAG, "查询完毕，刷新" + cursor.getCount()+"个元素");
        cursor.close();
    }

    public void printHashCode() {
        Log.d(TAG, "[printHashCode] " + mCallLogs.hashCode());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDuration, tvType;
        ImageView ivDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvType = itemView.findViewById(R.id.tv_type);
            ivDivider = itemView.findViewById(R.id.divider);
        }
    }

    private String formatDuration(long duration) {
        return "120秒";
    }

    private String formatCallType(int type) {
        switch (type) {
            case Calls.INCOMING_TYPE:
                return "呼入";
            case Calls.OUTGOING_TYPE:
                return "呼出";
            case Calls.MISSED_TYPE:
                return "未接来电";
            default:
                return "未知";
        }
    }

    public void clear() {
        if (mCursor != null) mCursor.close();
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

    void test(){
        mCallLogs.clear();
        notifyDataSetChanged();
    }
}

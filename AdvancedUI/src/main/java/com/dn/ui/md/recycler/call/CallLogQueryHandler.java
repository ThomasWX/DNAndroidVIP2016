package com.dn.ui.md.recycler.call;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog.Calls;

public class CallLogQueryHandler extends AsyncQueryHandler {
    public static final int QUERY_CALL_LOG_TOKEN = 111;
    private Listener listener;

    public CallLogQueryHandler(ContentResolver cr, Listener listener) {
        super(cr);
        this.listener = listener;
    }

    public void queryCallLogs(String number) {
        String selection = Calls.NUMBER + "=?";
        String[] selectionArgs = new String[]{number};
        startQuery(QUERY_CALL_LOG_TOKEN, null, Calls.CONTENT_URI, CallLogQuery.PROJECTION, selection, selectionArgs, Calls.DEFAULT_SORT_ORDER);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        listener.onQueryComplete(token, cookie, cursor);
    }

    public interface Listener {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
    }
}

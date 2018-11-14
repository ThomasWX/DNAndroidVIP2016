package com.dn.ui.md.recycler.call;

import android.provider.CallLog.Calls;

public class CallLogQuery {
    public static final String[] PROJECTION = {
            Calls._ID,
            Calls.DATE,
            Calls.DURATION,
            Calls.TYPE
    };
    public static final int ID = 0;
    public static final int DATE = 1;
    public static final int DURATION = 2;
    public static final int TYPE = 3;
}

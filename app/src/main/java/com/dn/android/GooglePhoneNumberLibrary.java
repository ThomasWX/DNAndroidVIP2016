package com.dn.android;
/*
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class GooglePhoneNumberLibrary extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextView;
    private AsYouTypeFormatter mFormatter;
    private static final String TAG = "GooglePhoneNumberLibrary";
    private int mType = 0;
    public static final int SIM_CONTACT = 1;
    public static final int NOT_SIM_CONTACT = 0;


    private final static char[] ACCEPT_NUM_CHARS = new char[]{'0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', // 0x0-0x9
            '*', '#', ',', '+', ';', '(', '.', ')'};
    private final static char[] ACCEPT_SIM_NUM_CHARS = new char[]{'0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', // 0x0-0x9
            '*', '#', ',', '+'};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        mTextView = findViewById(R.id.textView);
        mEditText = findViewById(R.id.editText);

        mFormatter = PhoneNumberUtil.getInstance().getAsYouTypeFormatter(getCurrentCountryIso(this));


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mTextView.setText(format(s, Selection.getSelectionEnd(s)));

            }
        };

        mEditText.addTextChangedListener(textWatcher);
    }

    */
/**
     * @param input
     * @param cursor 游标
     * @return
     *//*

    private CharSequence format(CharSequence input, int cursor) {
//        asYouTypeFormatter.inputDigit()
        return reformat(input, cursor);
    }


    private String reformat(CharSequence s, int cursor) {
        // The index of char to the leftward of the cursor.
        int curIndex = cursor - 1;
        String formatted = null;
        mFormatter.clear();
        char lastNonSeparator = 0;
        boolean hasCursor = false;
        int len = s.length();
        try {
            for (int i = 0; i < len; i++) {
                char c = s.charAt(i);
                if ((mType == NOT_SIM_CONTACT && isCharAcceptForNumber(c))
                        || (mType == SIM_CONTACT && isCharAcceptForSimNumber(c))) {
                    if (lastNonSeparator != 0) {
                        formatted = getFormattedNumber(lastNonSeparator, hasCursor);
                        hasCursor = false;
                    }
                    lastNonSeparator = c;
                }
                if (i == curIndex) {
                    hasCursor = true;
                }
            }
            if (lastNonSeparator != 0) {
                formatted = getFormattedNumber(lastNonSeparator, hasCursor);
            }
        } catch (Exception e) {
            android.util.Log.e(TAG, "s = " + s.toString()
                    + ", lastNonSeparator = " + lastNonSeparator
                    + ", hasCursor = " + hasCursor);
            e.printStackTrace();
        }

        return formatted;
    }

    private boolean isCharAcceptForNumber(char c) {
        for (char element :
                ACCEPT_NUM_CHARS) {
            if (c == element) {
                return true;
            }
        }
        return false;
    }

    private boolean isCharAcceptForSimNumber(char c) {
        for (char element :
                ACCEPT_SIM_NUM_CHARS) {
            if (c == element) {
                return true;
            }
        }
        return false;
    }


    private String getFormattedNumber(char lastNonSeparator, boolean hasCursor) {
        return hasCursor ? mFormatter.inputDigitAndRememberPosition(lastNonSeparator)
                : mFormatter.inputDigit(lastNonSeparator);
    }

    public String getCurrentCountryIso(Context context) {
        return context.getResources().getConfiguration().getLocales().get(0).getCountry();
    }

}
*/

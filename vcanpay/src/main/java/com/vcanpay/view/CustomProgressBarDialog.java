package com.vcanpay.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.vcanpay.R;


/**
 * Created by user007 on 2015/3/11.
 */
public class CustomProgressBarDialog extends Dialog {
    private static CustomProgressBarDialog customProgressBarDialog;
    private Context context;

    public CustomProgressBarDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressBarDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected CustomProgressBarDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static CustomProgressBarDialog createDialog(Context context) {
        customProgressBarDialog = new CustomProgressBarDialog(context, R.style.CustomProgressDialog);
        customProgressBarDialog.setContentView(R.layout.progress_bar_dialog);
        customProgressBarDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return customProgressBarDialog;
    }

    public CustomProgressBarDialog setTitle(String title) {
        return customProgressBarDialog;
    }

    public CustomProgressBarDialog setMessage(String message) {
        TextView messageView = (TextView) findViewById(R.id.message);
        if (messageView != null) {
            messageView.setText(message);
        }
        return customProgressBarDialog;
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }
}

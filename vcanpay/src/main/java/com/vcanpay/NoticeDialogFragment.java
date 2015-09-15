package com.vcanpay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import com.example.vcanpay.R;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class NoticeDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    int mTitle;
    int mMessage;
    int mPositiveButtonText;
    int mNegativeButtonText;

    public static NoticeDialogFragment getInstance(int title, int message, int positiveButtonText, int negativeButtonText) {
        NoticeDialogFragment dialog = new NoticeDialogFragment();
        dialog.mTitle = title;
        dialog.mMessage = message;
        dialog.mPositiveButtonText = positiveButtonText;
        dialog.mNegativeButtonText = negativeButtonText;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle == 0 ? R.string.notify : mTitle)
                .setMessage(mMessage);

        if (mPositiveButtonText != 0) {
            builder.setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListener != null) {
                        mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                    }
                }
            });
        }

        if (mNegativeButtonText != 0) {
            builder.setNegativeButton(mNegativeButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (mListener != null) {
                        mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                    }
                }
            });
        }

        return builder.create();
    }

    public void setNoticeDialogListener(NoticeDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }
}

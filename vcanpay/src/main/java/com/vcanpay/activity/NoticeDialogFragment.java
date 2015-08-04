package com.vcanpay.activity;

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
        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    String mMessage2;

    int mTitle;
    int mMessage;
    int mPositiveButtonText;
    int mNegativeButtonText;

    public void setMessage(String message) {
        mMessage2 = message;
    }

    public static NoticeDialogFragment getInstance(int title, int message, int positiveButtonText, int negativeButtonText) {
        NoticeDialogFragment dialog = new NoticeDialogFragment();
        dialog.mTitle = title;
        dialog.mMessage = message;
        dialog.mPositiveButtonText = positiveButtonText;
        dialog.mNegativeButtonText = negativeButtonText;
        return dialog;
    }

    public static NoticeDialogFragment getInstance(int title, String message, int positiveButtonText, int negativeButtonText) {
        NoticeDialogFragment dialog = new NoticeDialogFragment();
        dialog.mTitle = title;
        dialog.mMessage2 = message;
        dialog.mPositiveButtonText = positiveButtonText;
        dialog.mNegativeButtonText = negativeButtonText;
        return dialog;
    }


    public static NoticeDialogFragment getInstance(String message) {
        NoticeDialogFragment dialog = new NoticeDialogFragment();
        dialog.setMessage(message);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (mMessage == 0) {
            builder.setMessage(mMessage2);
        } else {

            builder.setMessage(mMessage);
        }
        builder.setPositiveButton(mPositiveButtonText == 0 ? R.string.ok : mPositiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (mListener != null) {
                    mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                }
            }
        })
                .setNegativeButton(mNegativeButtonText == 0 ? R.string.cancel : mNegativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mListener != null) {
                            mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                        }
                    }
                })
                .setTitle(mTitle == 0 ? R.string.notify : mTitle);
        // Create the AlertDialog object and return it
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
}

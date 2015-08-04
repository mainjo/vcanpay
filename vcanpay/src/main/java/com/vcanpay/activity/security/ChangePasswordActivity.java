package com.vcanpay.activity.security;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.NoticeDialogFragment;
import com.vcanpay.activity.SignInActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.password.ChangePasswordRequest;
import com.vcanpay.activity.password.ChangePasswordResponse;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class ChangePasswordActivity extends BaseActivity implements NoticeDialogFragment.NoticeDialogListener, TextWatcher {
    public static final String TAG = "ChangePasswordActivity";


    EditText mEtOldPassword;
    EditText mEtNewPassword;
    EditText mEtConfirmPassword;
    Button mBtnSubmit;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String oldPassword = mEtOldPassword.getText().toString();
            String newPassword = mEtNewPassword.getText().toString();
            String confirmPassword = mEtConfirmPassword.getText().toString();

            if (TextUtils.isEmpty(oldPassword)) {
                mEtOldPassword.setError(getString(R.string.password_not_empty));
            }

            if (TextUtils.isEmpty(newPassword)) {
                mEtNewPassword.setError(getString(R.string.password_not_empty));
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                mEtConfirmPassword.setError(getString(R.string.password_not_empty));
            }

            if (!newPassword.equals(confirmPassword)) {
                showAlertDialog(ChangePasswordActivity.this, getString(R.string.notify), getString(R.string.password_not_samne));
                return;
            }
            String email = getCurrentCustomer().getEmail();
            makeRequest(email, oldPassword, newPassword);
        }
    };

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
//                        intent.addFlags()
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isEmpty(mEtOldPassword.getText().toString()) &&
                !isEmpty(mEtNewPassword.getText().toString()) &&
                !isEmpty(mEtConfirmPassword.getText().toString())) {
            mBtnSubmit.setEnabled(true);
            return;
        }

        mBtnSubmit.setEnabled(false);
    }

    public boolean isEmpty(CharSequence text) {

        return TextUtils.isEmpty(text);
    }


    private class MyObject {
        private String email;
        private String oldPassword;
        private String newPassword;

        public MyObject() {
        }

        public MyObject(String email, String oldPassword, String newPassword) {
            this.email = email;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }
    }


    private void makeRequest(String email, String oldPassword, String newPassword) {

        MyObject object = new MyObject(email, oldPassword, newPassword);

//        Gson gson = new Gson();
//        String json1 = gson.toJson(object);

        String json1 = String.format("{" +
                        "\"oldPassWord\":\"%s\"," +
                        "\"email\":\"%s\"," +
                        "\"newPassWord\":\"%s\"}",
                object.oldPassword,
                object.email,
                object.newPassword
        );


        String json2 = "{updatePwd: " + json1 + "}";

        ChangePasswordRequest request = new ChangePasswordRequest(
                json2,
                json1,
                new Response.Listener<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(ChangePasswordResponse response) {
                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, R.string.change_password_success, 0, 0);
                        dialog.setNoticeDialogListener(ChangePasswordActivity.this);
                        dialog.show(getSupportFragmentManager(), "change_password_dialog");

                    }
                },
                new VolleyErrorListener(ChangePasswordActivity.this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mEtOldPassword = (EditText) findViewById(R.id.old_password);
        mEtNewPassword = (EditText) findViewById(R.id.new_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mBtnSubmit = (Button) findViewById(R.id.button_confirm);
        mBtnSubmit.setOnClickListener(listener);
    }

    @Override
    public String toString() {
        return getResources().getString(R.string.title_activity_change_password);
    }

}
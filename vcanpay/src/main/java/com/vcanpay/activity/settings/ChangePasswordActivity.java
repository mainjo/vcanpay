package com.vcanpay.activity.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.password.ChangePasswordRequest;
import com.vcanpay.activity.password.ChangePasswordResponse;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class ChangePasswordActivity extends BaseActivity {


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
                mEtConfirmPassword.setError(getString(R.string.password_not_empty));            }

            if (!newPassword.equals(confirmPassword)) {
                showAlertDialog(ChangePasswordActivity.this, getString(R.string.notify), getString(R.string.password_not_samne));
                return;
            }

            String email = "223153516@qq.com";
            makeRequest(email, oldPassword, newPassword);


        }
    };


    private class MyObject{
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

        Gson gson = new Gson();
        String json = gson.toJson(object);
        json = "{updatePwd: " + json + "}";

        ChangePasswordRequest request = new ChangePasswordRequest(
                json,
                new Response.Listener<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(ChangePasswordResponse response) {

                        showAlertDialog(ChangePasswordActivity.this, getString(R.string.notify), response.getMessage());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showAlertDialog(ChangePasswordActivity.this, getString(R.string.notify), error.getMessage());
                    }
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return getResources().getString(R.string.title_activity_change_password);
    }

}

package com.vcanpay.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.register.RegisterActivity;
import com.vcanpay.request.SignInRequest;
import com.vcanpay.response.SignInResponse;

import org.vcanpay.eo.Login;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class SignInActivity extends ActionBarActivity {
    public static final String TAG = "SignInActivity";

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
    private static final String PASSWORD_REGEX = "^[0-9a-zA-Z@*$#%^&?!.]{6,16}$";

    TextView mTvAccount;
    TextView mTvPassword;

    Button mSignIn;
    Button mRegister;

    String mAccount;
    String mPassword;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            mAccount = mTvAccount.getText().toString();
            mPassword = mTvPassword.getText().toString();

            switch (id) {
                case R.id.btn_sign_in:
                    signIn(mAccount, mPassword);
                    break;
                case R.id.btn_register:
                    startRegisterActivity(SignInActivity.this, RegisterActivity.class);
                    break;
            }
        }
    };

    private void signIn(String email, String password) {

        if (email == null || email.equals("") || password == null || password.equals("")) {
            showAlertDialog(this, getString(R.string.notify), getString(R.string.account_or_password_not_empty));
            return;
        }

        makeLoginRequest(email, password);
    }

    private void startRegisterActivity(Context context, Class<? extends Activity> clazz) {

        startActivity(context, clazz);
    }

    private void startActivity(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    private void makeLoginRequest(final String email, final String password) {

        Login login = new Login(email, password);

        Gson gson = new Gson();
        String json = gson.toJson(login);

        json = "{login:" + json + "}";

        Log.i(TAG, "User: " + email + " is signing in.");
        Log.i(TAG, "Post content body: " + json);

        SignInRequest request = new SignInRequest(
                json,
                new Response.Listener<SignInResponse>() {
                    @Override
                    public void onResponse(SignInResponse response) {
                        if (response.getStatusCode() == 200) {
                            startActivity(SignInActivity.this, TabhostActivity.class);
                        } else {
                            showAlertDialog(SignInActivity.this, getString(R.string.notify), response.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showAlertDialog(SignInActivity.this, getString(R.string.notify), error.getMessage());
                    }
                });

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mTvAccount = (TextView) findViewById(R.id.account);
        mTvPassword = (TextView) findViewById(R.id.password);

        mSignIn = (Button) findViewById(R.id.btn_sign_in);
        mRegister = (Button) findViewById(R.id.btn_register);
        mSignIn.setOnClickListener(listener);
        mRegister.setOnClickListener(listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

package com.vcanpay.activity.register;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.TabHostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

public class RequestConfirmEmailActivity extends BaseActivity implements View.OnClickListener{

    String  activationCode;

    EditText mEditText;
    Button mButton;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirm_email);

        Intent intent = getIntent();
        activationCode = intent.getStringExtra("activation_code");
        email = intent.getStringExtra("email");

        if (activationCode == null) {
            throw new IllegalStateException("activation code is null");
        }

        mEditText = (EditText) findViewById(R.id.edit_text);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String inputCode = mEditText.getText().toString();

        if (inputCode.equals("")) {
            showAlertDialog(this, getString(R.string.notify), getString(R.string.not_empty));
            return;
        }
        if (!inputCode.equals(activationCode)) {
            showAlertDialog(this, getString(R.string.notify), getString(R.string.activation_code_incorrect));
            return;
        }
        makeActivationRequest(email);
    }

    private void makeActivationRequest(String email) {
        ConfirmEmailRequest request = new ConfirmEmailRequest(
                email,
                new Response.Listener<ConfirmEmailResponse>() {
                    @Override
                    public void onResponse(ConfirmEmailResponse response) {
                        if (response.getStatusCode() == 204) {
                            Intent intent = new Intent(RequestConfirmEmailActivity.this, TabHostActivity.class);
                            startActivity(intent);
                        } else {
                            showAlertDialog(RequestConfirmEmailActivity.this, getString(R.string.notify), getString(R.string.verification_code_error));
                        }
                    }
                },
                new VolleyErrorListener(this){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        closeProgressDialog();
                        super.onErrorResponse(error);
                    }
                }
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
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
}

package com.vcanpay.activity.register;

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
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustomInfo;

public class RegisterActivityNext extends ActionBarActivity {

    EditText mEtEmail;
    EditText mEtPassword;
    EditText mEtConfirmPassword;
    EditText mEtFirstName;
    EditText mEtLastName;
    EditText mEtIdCard;
    EditText mEtCity;
    EditText mEtProvince;
    EditText mEtCountry;

    RadioGroup mIdentificationType;

    Button mSubmit;

    String email;
    String password;
    String confirm_password;
    String firstName;
    String lastName;
    String city;
    String province;
    String country;
    char identificationType;
    String idCard;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            email = mEtEmail.getText().toString();
            password = mEtPassword.getText().toString();
            confirm_password = mEtConfirmPassword.getText().toString();
            firstName = mEtFirstName.getText().toString();
            lastName = mEtLastName.getText().toString();
            city = mEtCity.getText().toString();
            province = mEtProvince.getText().toString();
            country = mEtCountry.getText().toString();
            idCard = mEtIdCard.getText().toString();

            int selectId = mIdentificationType.getCheckedRadioButtonId();
            if (selectId == R.id.radio_id_card) {
                identificationType = '1';
            } else if (selectId == R.id.radio_passport){
                identificationType = '2';
            }

            if (email.equals("")) {
                mEtEmail.setError(getString(R.string.email_not_empty));
            }

            if (password.equals("")) {
                mEtPassword.setError(getString(R.string.password_not_empty));
            }

            if (confirm_password.equals("")) {
                mEtConfirmPassword.setError(getString(R.string.confirm_password_not_empty));
            }

            if (firstName.equals("")) {
                mEtFirstName.setError(getString(R.string.first_name_not_empty));
            }

            if (lastName.equals("")) {
                mEtLastName.setError(getString(R.string.last_name_not_emtpy));
            }

            if (city.equals("")) {
                mEtCity.setError(getString(R.string.city_not_empty));
            }

            if (province.equals("")) {
                mEtProvince.setError(getString(R.string.province_not_empty));
            }

            if (country.equals("")) {
                mEtCountry.setError(getString(R.string.country_not_empty));
            }
            makeRegisterRequest();
        }
    };

    private void makeRegisterRequest() {

        CustomInfo customInfo = new CustomInfo();
        customInfo.setEmail(email);
        customInfo.setLoginPwd(password);
        customInfo.setFirstName(firstName);
        customInfo.setLastName(lastName);
        customInfo.setICardId(idCard);
        customInfo.setCredType('1');
        customInfo.setCity(city);
        customInfo.setProvince(province);
        customInfo.setCountry(country);

        Gson gson = new Gson();
        String json = gson.toJson(customInfo);

        Log.i("Test", "requestBody: " + json);

        json = "{customInfo:" + json + "}";

        RegisterRequest request = new RegisterRequest(
                json,
                new Response.Listener<RegisterResponse>() {
                    @Override
                    public void onResponse(RegisterResponse response) {
                        if (response.getStatusCode() == 201) {
                            requestSendEmail(email);
                        } else {
                            showAlertDialog(RegisterActivityNext.this, getString(R.string.notify), response.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showAlertDialog(RegisterActivityNext.this, getString(R.string.notify), error.getMessage());
                    }
                });

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    private void requestSendEmail(final String email) {
        SendEmailRequest request = new SendEmailRequest(
                email,
                new Response.Listener<SendEmailResponse>() {
                    @Override
                    public void onResponse(SendEmailResponse response) {
                        if (response.getStatusCode() == 200) {
                            Intent intent = new Intent(RegisterActivityNext.this, RequestConfirmEmailActivity.class);
                            intent.putExtra("activation_code", response.getMessage());
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            showAlertDialog(RegisterActivityNext.this, getString(R.string.notify), response.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showAlertDialog(RegisterActivityNext.this, getString(R.string.notify), error.getMessage());
                    }
                }
        );

        AppRequestQueue requestQueue = AppRequestQueue.getInstance(this);
        requestQueue.addToRequestQueue(request);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_next);

        init();
    }

    private void init() {
        mEtEmail = (EditText) findViewById(R.id.email);
        mEtPassword = (EditText) findViewById(R.id.password);
        mEtConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mEtFirstName = (EditText) findViewById(R.id.first_name);
        mEtLastName = (EditText) findViewById(R.id.last_name);
        mEtCity = (EditText) findViewById(R.id.city);
        mEtProvince = (EditText) findViewById(R.id.province);
        mEtCountry = (EditText) findViewById(R.id.country);

        mIdentificationType = (RadioGroup) findViewById(R.id.identification_type);
        mEtIdCard = (EditText) findViewById(R.id.id_card);

        Intent intent = getIntent();
        mEtEmail.setText(intent.getStringExtra("email"));

        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activity_next, menu);
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

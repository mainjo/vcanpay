package com.vcanpay.activity.register;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.profile.UpdateMobileActivity;
import com.vcanpay.activity.recharge.AreaContentProvider2;
import com.vcanpay.activity.recharge.ChooseRegionActivity;

import org.vcanpay.eo.CustomInfo;

public class RegisterActivityNext extends BaseActivity implements TextWatcher {

    EditText mEtEmail;
    EditText mEtPassword;
    EditText mEtConfirmPassword;
    EditText mEtFirstName;
    EditText mEtLastName;
    EditText mEtIdCard;
    EditText mEtCity;
//    EditText mEtProvince;
//    EditText mEtCountry;

    Spinner mSpProvince;
    Spinner mSpCountry;

    RadioGroup mIdentificationType;
//    Spinner mIdentityType;

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

    static AreaContentProvider2 mDb;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            email = mEtEmail.getText().toString();
            password = mEtPassword.getText().toString();
            confirm_password = mEtConfirmPassword.getText().toString();
            firstName = mEtFirstName.getText().toString();
            lastName = mEtLastName.getText().toString();
            city = mEtCity.getText().toString();
//            province = mEtProvince.getText().toString();
//            country = mEtCountry.getText().toString();

            province = String.valueOf(((ChooseRegionActivity.Area) mSpProvince.getSelectedItem()).areaId);
            country = String.valueOf(((UpdateMobileActivity.Country) mSpCountry.getSelectedItem()).id);

            idCard = mEtIdCard.getText().toString();

            int selectId = mIdentificationType.getCheckedRadioButtonId();
            if (selectId == R.id.radio_id_card) {
                identificationType = '1';
            } else if (selectId == R.id.radio_passport) {
                identificationType = '2';
            }

            if (!confirm_password.equals(password)) {
                showAlertDialog(RegisterActivityNext.this, getString(R.string.notify), getString(R.string.password_not_consistent));
                return;
            }

            makeRegisterRequest();
        }
    };

    private void makeRegisterRequest() {
        showProgressDialog(this);

        CustomInfo customInfo = new CustomInfo();
        customInfo.setEmail(email);
        customInfo.setLoginPwd(password);
        customInfo.setFirstName(firstName);
        customInfo.setLastName(lastName);
        customInfo.setICardId(idCard);
        customInfo.setCredType("1");
        customInfo.setCity(city);
        customInfo.setProvince(province);
        customInfo.setCountry(country);

//        Gson gson = new Gson();
//        String json1 = gson.toJson(customInfo);

        String json1 = String.format(
                "{\"customId\":0," +
                        "\"city\":\"%s\"," +
                        "\"country\":\"%s\"," +
                        "\"credType\":\"%s\"," +
                        "\"customScore\":0," +
                        "\"email\":\"%s\"," +
                        "\"firstName\":\"%s\"," +
                        "\"ICardId\":\"%s\"," +
                        "\"lastName\":\"%s\"," +
                        "\"loginPwd\":\"%s\"," +
                        "\"province\":\"%s\"," +
                        "\"loginErrTimes\":0}",
                customInfo.getCity(),
                customInfo.getCountry(),
                customInfo.getCredType(),
                customInfo.getEmail(),
                customInfo.getFirstName(),
                customInfo.getICardId(),
                customInfo.getLastName(),
                customInfo.getLoginPwd(),
                customInfo.getProvince());

        Log.i("json", json1);


        String json2 = "{customInfo:" + json1 + "}";

        RegisterRequest request = new RegisterRequest(
                json2,
                json1,
                new Response.Listener<RegisterResponse>() {
                    @Override
                    public void onResponse(RegisterResponse response) {
                        closeProgressDialog();
                        Intent intent = new Intent(RegisterActivityNext.this, ActivateAccountActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("EMAIL", email);
//                        Intent intent = new Intent(RegisterActivityNext.this, SignInActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                },
                new VolleyErrorListener(this));

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_next);

        init();
    }

    private void init() {

        if (mDb == null) {
            mDb = new AreaContentProvider2(this);
        }

        mEtEmail = (EditText) findViewById(R.id.email);
        mEtPassword = (EditText) findViewById(R.id.password);
        mEtConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mEtFirstName = (EditText) findViewById(R.id.first_name);
        mEtLastName = (EditText) findViewById(R.id.last_name);
        mEtCity = (EditText) findViewById(R.id.city);
//        mEtProvince = (EditText) findViewById(R.id.province);
//        mEtCountry = (EditText) findViewById(R.id.country);
        mSpProvince = (Spinner) findViewById(R.id.spinner_province);
        mSpCountry = (Spinner) findViewById(R.id.spinner_country);


        mSpProvince.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getAreaList(1, mDb)));
        mSpCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, UpdateMobileActivity.Country.countries));


//        mIdentityType = (Spinner) findViewById(R.id.identityType);
//        mIdentityType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]));
        mIdentificationType = (RadioGroup) findViewById(R.id.identification_type);
        mEtIdCard = (EditText) findViewById(R.id.id_card);

        Intent intent = getIntent();
        mEtEmail.setText(intent.getStringExtra("email"));

        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(listener);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public static class IdentityType {
        public int id;
        public String text;

        public IdentityType(int id, String text) {
            this.id = id;
            this.text = text;

        }
    }

    public static class IdentityTypeDummp {
        public static final IdentityType[] items = new IdentityType[2];

        static {
            items[0] = new IdentityType(1, "\u8eab\u4efd\u8bc1");
            items[1] = new IdentityType(2, "\u62a4\u7167");
        }
    }
}

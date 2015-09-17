package com.vcanpay.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.MyApplication;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.help.HelpActivity;
import com.vcanpay.activity.password.ResetPasswordActivity;
import com.vcanpay.activity.register.ActivateAccountActivity;
import com.vcanpay.activity.register.RegisterActivity;
import com.vcanpay.exception.EmailNotConfirmedException;
import com.vcanpay.exception.EmailNotExsitException;
import com.vcanpay.exception.PasswordIncorrectException;
import com.vcanpay.request.SignInRequest;
import com.vcanpay.response.SignInResponse;
import com.vcanpay.validator.EmailValidator;
import com.vcanpay.validator.NotEmptyValidator;
import com.vcanpay.view.LangFlagView;

import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.Login;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class SignInActivity extends BaseActivity implements TextWatcher, View.OnClickListener {
    public static final String TAG = "SignInActivity";

    public static final String IS_FIRST_SIGN_IN = "is_first_sign_in";
    public static final boolean isFirstSignIn = true;

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
    private static final String PASSWORD_REGEX = "^[0-9a-zA-Z@*$#%^&?!.]{6,16}$";

    TextView mEtAccount;
    TextView mEtPassword;
    TextView mTvForgetPassword;

    Button mSignIn;
    Button mRegister;
    View mUserGuide;

    String mAccount;
    String mPassword;

    // 语言切换
    LangFlagView mIvChinese;
    LangFlagView mIvEnglish;
    LangFlagView mIvThailand;


    private SharedPreferences sharedPreferences;
    private String currentLanguage;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            mAccount = mEtAccount.getText().toString();
            mPassword = mEtPassword.getText().toString();

            switch (id) {
                case R.id.action_sign_in:
                    signIn(mAccount, mPassword);
                    break;
                case R.id.action_register:
                    startRegisterActivity(SignInActivity.this, RegisterActivity.class);
                    break;
            }
        }
    };

    private void signIn(String email, String password) {

        makeLoginRequest(email, password);
    }

    private void startRegisterActivity(Context context, Class<? extends Activity> clazz) {

        startActivity(context, clazz);
    }


    private void makeLoginRequest(final String email, final String password) {
        showProgressDialog(this);
        Login login = new Login(email, password);
        Gson gson = new Gson();
        String json1 = gson.toJson(login);

        Log.i("TEST", "begin login");
        String json2 = "{\"login\":" + json1 + "}";
        SignInRequest request = new SignInRequest(
                json2,
                json1,
                new Response.Listener<SignInResponse>() {
                    @Override
                    public void onResponse(SignInResponse response) {
                        closeProgressDialog();
                        CustomInfo customInfo = response.getCustomInfo();

                        // 保存当前登录的用户
                        ((MyApplication)getApplication()).setCustomInfo(customInfo);

                        saveSignInStatus(false);
                        saveCustomer(mEtAccount.getText().toString(), mEtPassword.getText().toString());
//                        saveCustomer(customInfo);
                        Intent intent = new Intent(SignInActivity.this, TabHostActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                },
                new VolleyErrorListener(this){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        closeProgressDialog();
                        if (error instanceof EmailNotExsitException) {
                            Toast.makeText(SignInActivity.this, R.string.email_not_exist, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (error instanceof EmailNotConfirmedException) {
                            Toast.makeText(SignInActivity.this, R.string.email_not_activated, Toast.LENGTH_LONG).show();
                            mActivity.startActivity(new Intent(SignInActivity.this, ActivateAccountActivity.class));
                            return;
                        }

                        if (error instanceof PasswordIncorrectException) {
                            Toast.makeText(SignInActivity.this, R.string.passwor_incorrect, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (error instanceof SignInRequest.PasswordErrorUpTo3TimesException) {
                            Toast.makeText(SignInActivity.this, R.string.password_error_3_time, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (error instanceof SignInRequest.PasswordErrorUpTo6TimesException) {
                            Toast.makeText(SignInActivity.this, R.string.password_error_6_time, Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (error instanceof SignInRequest.PasswordErrorUpTo9TimesException) {
                            Toast.makeText(SignInActivity.this, R.string.password_error_9_time, Toast.LENGTH_LONG).show();
                            return;
                        }
                        super.onErrorResponse(error);
                    }
                });

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(R.string.sign_in);
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
        initView();

        sharedPreferences = getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE);
    }

    private void initView() {

//        mToolBar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolBar);
        setTitle(R.string.sign_in);

        mEtAccount = (TextView) findViewById(R.id.account);
        mEtPassword = (TextView) findViewById(R.id.password);
        mEtAccount.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);

        mSignIn = (Button) findViewById(R.id.action_sign_in);
        mRegister = (Button) findViewById(R.id.action_register);
        mSignIn.setOnClickListener(listener);
        mRegister.setOnClickListener(listener);

//        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvForgetPassword = (TextView) findViewById(R.id.forget_password);
        mTvForgetPassword.setOnClickListener(this);

        //语言选择
        mIvChinese = (LangFlagView) findViewById(R.id.ivChinese);
        mIvEnglish = (LangFlagView) findViewById(R.id.ivEnglish);
        mIvThailand = (LangFlagView) findViewById(R.id.ivThailand);

        mIvChinese.setOnClickListener(this);
        mIvEnglish.setOnClickListener(this);
        mIvThailand.setOnClickListener(this);


        mUserGuide = findViewById(R.id.user_guide);
        mUserGuide.setOnClickListener(this);

        // 设置选中的语言
        String selectedLang = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (selectedLang == null) {
            selectedLang = PreferenceManager.getDefaultSharedPreferences(this).getString("language_list", DEFAULT_LANG);
        }

        if (selectedLang.equals(ZH)) {
            mIvChinese.setChecked(true);
        }

        if (selectedLang.equals(EN)) {
            mIvEnglish.setChecked(true);
        }

        if (selectedLang.equals(TH)) {
            mIvThailand.setChecked(true);
        }
    }


    private void refreshConfig(String language_code) {

        Log.i("TEST", "refreshing view");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (currentLanguage == null) {
            currentLanguage = sp.getString("language_list", DEFAULT_LANG);
        }
        sp.edit().putString("language_list", language_code).apply();
        reload(language_code);
    }

    public void reload(String langCode) {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        intent.putExtra(Intent.EXTRA_TEXT, langCode);
        startActivity(intent);
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("TEST", "onConfigurationChanged");
    }

    public void saveSignInStatus(boolean firstSignIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_SIGN_IN, firstSignIn).apply();
    }

    public void saveCustomer(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUSTOMER_EMAIL, email);
        editor.putString(CUSTOMER_PASSWORD, password);
        editor.apply();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (validate(mEtAccount, new EmailValidator()) && validate(mEtPassword, new NotEmptyValidator())) {
            mSignIn.setEnabled(true);
        } else {
            mSignIn.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (currentLanguage == null) {
            currentLanguage = PreferenceManager.getDefaultSharedPreferences(this).getString("language_list", DEFAULT_LANG);
        }
        switch (id) {
            case R.id.ivChinese:
                if (!currentLanguage.equals(ZH)) {
                    mIvChinese.setChecked(true);
                    mIvEnglish.setChecked(false);
                    mIvThailand.setChecked(false);
                    refreshConfig(ZH);
                }
                break;

            case R.id.ivEnglish:
                if (!currentLanguage.equals(EN)) {
                    mIvChinese.setChecked(false);
                    mIvEnglish.setChecked(true);
                    mIvThailand.setChecked(false);
                    refreshConfig(EN);
                }
                break;

            case R.id.ivThailand:
                if (!currentLanguage.equals(TH)) {
                    mIvChinese.setChecked(false);
                    mIvEnglish.setChecked(false);
                    mIvThailand.setChecked(true);
                    refreshConfig(TH);
                }
                break;

            case R.id.agreement:
                openAgreement(AgreementAndPrivacyActivity.USER_AGREEMENT);
                break;

            case R.id.privacy:
                openAgreement(AgreementAndPrivacyActivity.USER_PRIVACY);
                break;

            case R.id.user_guide:
                openUserGuide();
                break;
            case R.id.forget_password:
                startActivity(this, ResetPasswordActivity.class);
        }
    }

    private void openUserGuide() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void openAgreement(String field) {

        if (field == null) {
            field = AgreementAndPrivacyActivity.USER_AGREEMENT;
        }

        Intent intent = new Intent(this, AgreementAndPrivacyActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, field);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentLanguage != null) {
            currentLanguage = null;
        }

        if (sharedPreferences != null) {
            sharedPreferences = null;
        }

        if (listener != null) {
            listener = null;
        }
    }
}

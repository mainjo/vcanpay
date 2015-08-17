package com.vcanpay.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.MyApplication;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.help.UserGuideActivity;
import com.vcanpay.activity.password.ResetPasswordActivity;
import com.vcanpay.activity.register.RegisterActivity;
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
    TextView mTvRegister;
    TextView mTvForgetPassword;

    Button mSignIn;
//    Button mRegister;

    View mAgreement;
    View mPrivacy;
    View mUserGuide;

    String mAccount;
    String mPassword;

//    Button mBtnForgetPassword;


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
                case R.id.btn_sign_in:
                    signIn(mAccount, mPassword);
                    break;
//                case R.id.btn_register:
//                    startRegisterActivity(SignInActivity.this, RegisterActivity.class);
//                    break;
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
                        Intent intent = new Intent(SignInActivity.this, TabhostActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                },
                new VolleyErrorListener(this));

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
        initView();

        sharedPreferences = getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE);
    }

    private void initView() {
        mEtAccount = (TextView) findViewById(R.id.account);
        mEtPassword = (TextView) findViewById(R.id.password);
        mEtAccount.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);

        mSignIn = (Button) findViewById(R.id.btn_sign_in);
//        mRegister = (Button) findViewById(R.id.btn_register);
        mSignIn.setOnClickListener(listener);
//        mRegister.setOnClickListener(listener);

        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);

//        mBtnForgetPassword = (Button) findViewById(R.id.forget_password);
//        mBtnForgetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(SignInActivity.this, ResetPasswordActivity.class);
//            }
//        });


        //语言选择
        mIvChinese = (LangFlagView) findViewById(R.id.ivChinese);
        mIvEnglish = (LangFlagView) findViewById(R.id.ivEnglish);
        mIvThailand = (LangFlagView) findViewById(R.id.ivThailand);

        mIvChinese.setOnClickListener(this);
        mIvEnglish.setOnClickListener(this);
        mIvThailand.setOnClickListener(this);


        mAgreement = findViewById(R.id.agreement);
        mPrivacy = findViewById(R.id.privacy);
        mUserGuide = findViewById(R.id.user_guide);

        mAgreement.setOnClickListener(this);
        mPrivacy.setOnClickListener(this);
        mUserGuide.setOnClickListener(this);

        String registerString = getResources().getString(R.string.register);
        String forgetPasswordString = getResources().getString(R.string.forget_password);

        SpannableString registerLink = new SpannableString(registerString);
        SpannableString forgetPasswordLink = new SpannableString(forgetPasswordString);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                int id = widget.getId();
                Context context = SignInActivity.this;
                switch (id) {
                    case R.id.tv_register:
                        startRegisterActivity(SignInActivity.this, RegisterActivity.class);
                        break;
                    case R.id.tv_forget_password:
                        startActivity(SignInActivity.this, ResetPasswordActivity.class);
                }
            }
        };


        ColorStateList colorStateList = getResources().getColorStateList(R.color.link_color);


        registerLink.setSpan(clickableSpan, 0, registerString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerLink.setSpan(new TextAppearanceSpan("sans-serif", Typeface.NORMAL, 24, colorStateList, colorStateList), 0, registerLink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forgetPasswordLink.setSpan(clickableSpan, 0, forgetPasswordString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgetPasswordLink.setSpan(new TextAppearanceSpan("sans-serif", Typeface.NORMAL, 24, colorStateList, colorStateList), 0, forgetPasswordLink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTvRegister.setText(registerLink);
        mTvForgetPassword.setText(forgetPasswordLink);

        mTvRegister.setMovementMethod(MyLinkMovementMethod.getInstance());
        mTvForgetPassword.setMovementMethod(MyLinkMovementMethod.getInstance());

        setTitle(R.string.sign_in);

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
        }
    }

    private void openUserGuide() {
        Intent intent = new Intent(this, UserGuideActivity.class);
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

    public class MyLinkMovementMethod extends LinkMovementMethod {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
            return super.onTouchEvent(widget, buffer, event);
        }
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

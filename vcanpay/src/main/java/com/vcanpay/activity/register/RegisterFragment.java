package com.vcanpay.activity.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.AgreementAndPrivacyActivity;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.validator.EmailValidator;
import com.vcanpay.validator.Validator;
import com.vcanpay.view.CustomProgressBarDialog;

public class RegisterFragment extends Fragment implements TextWatcher, View.OnClickListener {
    EditText mEtEmail;
    Button mButton;

    View mAgreement;
    View mPrivacy;

    private OnFragmentInteractionListener mListener;

    public static RegisterFragment newInstance(String param1, String param2) {
        return new RegisterFragment();
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mButton = (Button) view.findViewById(R.id.next);
        mEtEmail = (EditText) view.findViewById(R.id.email);

        mEtEmail.addTextChangedListener(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                requestValidateEmail(email);
            }
        });

        mAgreement = view.findViewById(R.id.agreement);
        mPrivacy = view.findViewById(R.id.privacy);

        mAgreement.setOnClickListener(this);
        mPrivacy.setOnClickListener(this);

        return view;
    }

    public void requestValidateEmail(final String email) {
        showProgressDialog(getActivity());

        ValidEmailRequest request = new ValidEmailRequest(
                email,
                new Response.Listener<ValidEmailResponse>() {
                    @Override
                    public void onResponse(ValidEmailResponse response) {
                        closeProgressDialog();

                        String message = response.getMessage();
                        if ("true".equals(message)) {
                            Intent intent = new Intent(getActivity(), RegisterActivityNext.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            return;
                        }
                        if ("false".equals(message)) {
                            showAlertDialog(getActivity(),
                                    getString(R.string.notify),
                                    getString(R.string.email_has_been_registered)
                                    );
                        }

                    }
                },
                new VolleyErrorListener((BaseActivity)getActivity()){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);

                    }
                });

        AppRequestQueue queue = AppRequestQueue.getInstance(getActivity());
        queue.addToRequestQueue(request);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    boolean validate(TextView textView, Validator validator) {
        return validator.validate(textView.getText());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (validate(mEtEmail, new EmailValidator())) {
            mButton.setEnabled(true);
        } else {
            mButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.agreement:
                openAgreement(AgreementAndPrivacyActivity.USER_AGREEMENT);
                break;

            case R.id.privacy:
                openAgreement(AgreementAndPrivacyActivity.USER_PRIVACY);
                break;
        }
    }

    public void openAgreement(String field) {

        if (field == null) {
            field = AgreementAndPrivacyActivity.USER_AGREEMENT;
        }

        Intent intent = new Intent(getActivity(), AgreementAndPrivacyActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, field);
        startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

    Dialog mDialog;

    protected void showProgressDialog(Context context) {
        mDialog = CustomProgressBarDialog.createDialog(context);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    protected void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}

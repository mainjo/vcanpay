package com.vcanpay.activity.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.activity.BaseActivity;

import org.vcanpay.eo.CustomInfo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SendMoneyActivity extends BaseActivity implements TextWatcher{

    EditText mEtAmount;
    EditText mEtEmail;
    EditText mEtNote;

    Button mOk;

    public static final String TRANSFER_REQUEST = "transfer_request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        mEtEmail = (EditText) findViewById(R.id.email);
        mEtAmount = (EditText) findViewById(R.id.amount);
        mEtNote = (EditText) findViewById(R.id.note);

        mEtEmail.addTextChangedListener(this);
        mEtAmount.addTextChangedListener(this);
        mEtNote.addTextChangedListener(this);

        mOk = (Button) findViewById(R.id.action_ok);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                BigDecimal amount = new BigDecimal(mEtAmount.getText().toString());
                String note = mEtNote.getText().toString();

                TransferRequestObject transferRequestObject = new TransferRequestObject();
                transferRequestObject.amount = amount;
                transferRequestObject.receiverEmail = email;
                transferRequestObject.note = note;

                transferRequestObject.customer = getCurrentCustomer();

                Intent intent = new Intent(SendMoneyActivity.this, SendMoneyConfirmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TRANSFER_REQUEST, transferRequestObject);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer, menu);
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

        if (id == R.id.action_ok) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeRequest() {

        TransferRequestObject object = new TransferRequestObject();
        object.amount = new BigDecimal(mEtAmount.getText().toString());
        object.receiverEmail = mEtEmail.getText().toString();

        Gson gson = new Gson();

        String json1 = gson.toJson(object);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isEmpty(mEtEmail.getText().toString()) &&
                !isEmpty(mEtAmount.getText().toString())) {
            mOk.setEnabled(true);
        } else {
            mOk.setEnabled(false);
        }
    }

    public boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    public static class TransferRequestObject implements Serializable{
        public BigDecimal amount;
        public String receiverEmail;
        public String note;
        public CustomInfo customer;

        public TransferRequestObject() {

        }

//        public TransferRequestObject(Parcel in) {
//            amount = in.readDouble();
//            receiverEmail = in.readString();
//            note = in.readString();
//            customer = (CustomInfo) in.readSerializable();
//        }
//
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            out.writeDouble(amount);
//            out.writeString(receiverEmail);
//            out.writeString(note);
//            out.writeSerializable(customer);
//        }
//
//        public static Creator<TransferRequestObject> CREATOR = new Creator<TransferRequestObject>() {
//            @Override
//            public TransferRequestObject createFromParcel(Parcel source) {
//                return new TransferRequestObject(source);
//            }
//
//            @Override
//            public TransferRequestObject[] newArray(int size) {
//                return new TransferRequestObject[0];
//            }
//        };
    }
}

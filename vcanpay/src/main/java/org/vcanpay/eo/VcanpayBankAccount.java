package org.vcanpay.eo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the vcanpay_bank_account database table.
 */
public class VcanpayBankAccount implements Serializable, Parcelable {
    private static final long serialVersionUID = 1L;

    private int vcpBankAccId;

    private String accountHolder;

    private String bankAddrNo;

    private String bankCardName;

    private String bankCardNo;

    private String bankName;

    private String bankResultNote;

    private String branchName;

    private String country;

    private String currency;

    private BigDecimal fee;

    private String province;

    private String region;

    private String state;

    private Date timeAccount;

    private Date timeStop;

    public VcanpayBankAccount() {
    }

    public int getVcpBankAccId() {
        return this.vcpBankAccId;
    }

    public void setVcpBankAccId(int vcpBankAccId) {
        this.vcpBankAccId = vcpBankAccId;
    }

    public String getAccountHolder() {
        return this.accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBankAddrNo() {
        return this.bankAddrNo;
    }

    public void setBankAddrNo(String bankAddrNo) {
        this.bankAddrNo = bankAddrNo;
    }

    public String getBankCardName() {
        return this.bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNo() {
        return this.bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankResultNote() {
        return this.bankResultNote;
    }

    public void setBankResultNote(String bankResultNote) {
        this.bankResultNote = bankResultNote;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTimeAccount() {
        return this.timeAccount;
    }

    public void setTimeAccount(Date timeAccount) {
        this.timeAccount = timeAccount;
    }

    public Date getTimeStop() {
        return this.timeStop;
    }

    public void setTimeStop(Date timeStop) {
        this.timeStop = timeStop;
    }

    @Override
    public String toString() {
        return bankName + "-" + bankCardNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new Object[]{
                this.accountHolder,
                this.bankAddrNo,
                this.bankCardName,
                this.bankCardNo,
                this.bankName,
                this.bankResultNote,
                this.branchName,
                this.country,
                this.currency,
                this.fee,
                this.province,
                this.region,
                this.state,
                this.timeAccount,
                this.timeStop,
                this.vcpBankAccId
        });
    }

    private void readFromParcel(Parcel in) {
        Object[] data = in.readArray(this.getClass().getClassLoader());
        this.accountHolder = (String) data[0];
        this.bankAddrNo = (String) data[1];
        this.bankCardName = (String) data[2];
        this.bankCardNo = (String) data[3];
        this.bankName = (String) data[4];
        this.bankResultNote = (String) data[5];
        this.branchName = (String) data[6];
        this.country = (String) data[7];
        this.currency = (String) data[8];
        this.fee = (BigDecimal) data[9];
        this.province = (String) data[10];
        this.region = (String) data[11];
        this.state = (String) data[12];
        this.timeAccount = (Date) data[13];
        this.timeStop= (Date) data[14];
        this.vcpBankAccId= (int) data[15];
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public VcanpayBankAccount createFromParcel(Parcel in) {
            return new VcanpayBankAccount(in);
        }

        public VcanpayBankAccount[] newArray(int size) {
            return new VcanpayBankAccount[size];
        }
    };

    public VcanpayBankAccount(Parcel in) {
        readFromParcel(in);
    }
}
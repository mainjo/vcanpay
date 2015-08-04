package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the pay_bill database table.
 */
public class PayBill implements Serializable {
    private static final long serialVersionUID = 1L;

    private int custPayId;

    private String checkState;

    private Date createTime;

    private String custPaySn;

    private Date expireTime;

    private BigDecimal fee;

    private String feeType;

    private String orderId;

    private String orderNote;

    private String payBankCardNo;

    private String payBankName;

    private String payeeAccountNo;

    private BigDecimal payeeMoney;

    private String payCompanyName;


    private String payState;

    private String settleDateTime;

    private String state;

    private String tranType;

    private int seller_id;


    private String paymethod;
    private String paycurrency;


    //bi-directional many-to-one association to CustomInfo
    private CustomInfo customInfo;

    public PayBill() {
    }

    public int getCustPayId() {
        return this.custPayId;
    }

    public void setCustPayId(int custPayId) {
        this.custPayId = custPayId;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCustPaySn() {
        return this.custPaySn;
    }

    public void setCustPaySn(String custPaySn) {
        this.custPaySn = custPaySn;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNote() {
        return this.orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getPayBankCardNo() {
        return this.payBankCardNo;
    }

    public void setPayBankCardNo(String payBankCardNo) {
        this.payBankCardNo = payBankCardNo;
    }

    public String getPayBankName() {
        return this.payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public String getPayeeAccountNo() {
        return this.payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public BigDecimal getPayeeMoney() {
        return this.payeeMoney;
    }

    public void setPayeeMoney(BigDecimal payeeMoney) {
        this.payeeMoney = payeeMoney;
    }

    public String getPayState() {
        return this.payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getSettleDateTime() {
        return this.settleDateTime;
    }

    public void setSettleDateTime(String settleDateTime) {
        this.settleDateTime = settleDateTime;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public CustomInfo getCustomInfo() {
        return this.customInfo;
    }

    public void setCustomInfo(CustomInfo customInfo) {
        this.customInfo = customInfo;
    }


    public String getPayCompanyName() {
        return payCompanyName;
    }

    public void setPayCompanyName(String payCompanyName) {
        this.payCompanyName = payCompanyName;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getPaycurrency() {
        return paycurrency;
    }

    public void setPaycurrency(String paycurrency) {
        this.paycurrency = paycurrency;
    }

}
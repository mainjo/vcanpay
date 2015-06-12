package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the withdraw_bill database table.
 * 
 */
public class WithdrawBill implements Serializable {
	private static final long serialVersionUID = 1L;
	private int custWithdrawId;

	private BigDecimal amount;

	private String bankAddrNo;

	private String bankCardName;

	private String bankCardNo;

	private String bankName;

	private String bankResultCode;

	private String bankResultNote;

	private Date createTime;

	private String custBankCode;

	private String custBankType;

	private BigDecimal fee;

	private String feeCustId;

	private String feeState;

	private String freezeSN;

	private String netNo;

	private String payState;

	private String settleDateTime;

	private String state;

	private String withdrawMode;

	private String withdrawSn;

	private String withdrawType;

	//bi-directional many-to-one association to CustomInfo
	private CustomInfo customInfo;

	public WithdrawBill() {
	}

	public int getCustWithdrawId() {
		return this.custWithdrawId;
	}

	public void setCustWithdrawId(int custWithdrawId) {
		this.custWithdrawId = custWithdrawId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getBankResultCode() {
		return this.bankResultCode;
	}

	public void setBankResultCode(String bankResultCode) {
		this.bankResultCode = bankResultCode;
	}

	public String getBankResultNote() {
		return this.bankResultNote;
	}

	public void setBankResultNote(String bankResultNote) {
		this.bankResultNote = bankResultNote;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustBankCode() {
		return this.custBankCode;
	}

	public void setCustBankCode(String custBankCode) {
		this.custBankCode = custBankCode;
	}

	public String getCustBankType() {
		return this.custBankType;
	}

	public void setCustBankType(String custBankType) {
		this.custBankType = custBankType;
	}

	public BigDecimal getFee() {
		return this.fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getFeeCustId() {
		return this.feeCustId;
	}

	public void setFeeCustId(String feeCustId) {
		this.feeCustId = feeCustId;
	}

	public String getFeeState() {
		return this.feeState;
	}

	public void setFeeState(String feeState) {
		this.feeState = feeState;
	}

	public String getFreezeSN() {
		return this.freezeSN;
	}

	public void setFreezeSN(String freezeSN) {
		this.freezeSN = freezeSN;
	}

	public String getNetNo() {
		return this.netNo;
	}

	public void setNetNo(String netNo) {
		this.netNo = netNo;
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

	public String getWithdrawMode() {
		return this.withdrawMode;
	}

	public void setWithdrawMode(String withdrawMode) {
		this.withdrawMode = withdrawMode;
	}

	public String getWithdrawSn() {
		return this.withdrawSn;
	}

	public void setWithdrawSn(String withdrawSn) {
		this.withdrawSn = withdrawSn;
	}

	public String getWithdrawType() {
		return this.withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

	public CustomInfo getCustomInfo() {
		return this.customInfo;
	}

	public void setCustomInfo(CustomInfo customInfo) {
		this.customInfo = customInfo;
	}

}
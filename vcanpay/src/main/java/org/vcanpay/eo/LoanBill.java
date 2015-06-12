package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the loan_bill database table.
 * 
 */
public class LoanBill implements Serializable {
	private static final long serialVersionUID = 1L;
	private int loanId;

	private String additional;

	private String confirmCode;

	private Date createTime;

	private BigDecimal fee;

	private String feeId;

	private String feeState;

	private String isOtherMoney;

	private BigDecimal loanAmount;

	private String loanEmail;

	private String loanName;

	private String loanRemarks;

	private String loanSn;

	private String loanState;

	private String loanType;

	private String moneyType;

	private Date overTime;

	private String payFrom;

	private String payMode;

	private BigDecimal payMoney;

	//bi-directional many-to-one association to CustomInfo
	private CustomInfo customInfo;

	public LoanBill() {
	}

	public int getLoanId() {
		return this.loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public String getAdditional() {
		return this.additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public String getConfirmCode() {
		return this.confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getFee() {
		return this.fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getFeeId() {
		return this.feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	public String getFeeState() {
		return this.feeState;
	}

	public void setFeeState(String feeState) {
		this.feeState = feeState;
	}

	public String getIsOtherMoney() {
		return this.isOtherMoney;
	}

	public void setIsOtherMoney(String isOtherMoney) {
		this.isOtherMoney = isOtherMoney;
	}

	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanEmail() {
		return this.loanEmail;
	}

	public void setLoanEmail(String loanEmail) {
		this.loanEmail = loanEmail;
	}

	public String getLoanName() {
		return this.loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanRemarks() {
		return this.loanRemarks;
	}

	public void setLoanRemarks(String loanRemarks) {
		this.loanRemarks = loanRemarks;
	}

	public String getLoanSn() {
		return this.loanSn;
	}

	public void setLoanSn(String loanSn) {
		this.loanSn = loanSn;
	}

	public String getLoanState() {
		return this.loanState;
	}

	public void setLoanState(String loanState) {
		this.loanState = loanState;
	}

	public String getLoanType() {
		return this.loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getMoneyType() {
		return this.moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Date getOverTime() {
		return this.overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public String getPayFrom() {
		return this.payFrom;
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}

	public String getPayMode() {
		return this.payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public CustomInfo getCustomInfo() {
		return this.customInfo;
	}

	public void setCustomInfo(CustomInfo customInfo) {
		this.customInfo = customInfo;
	}

}
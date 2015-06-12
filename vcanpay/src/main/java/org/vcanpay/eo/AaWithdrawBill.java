package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the aa_withdraw_bill database table.
 * 
 */
public class AaWithdrawBill implements Serializable {
	private static final long serialVersionUID = 1L;

	private int aaWithdrawId;

	private String aaNumber;

	private String aaWithdrawSn;

	private String additional1;

	private String additional2;

	private Date createTime;

	private BigDecimal fee;

	private String feeCustId;

	private String feeState;

	private String isOtherMoney;

	private String moneyType;

	private BigDecimal payAmount;

	private String payChannel;

	private String payEmail;

	private String payName;

	private String payPhone;

	private String showEmailOrNot;

	private String state;

	private String withdrawRemarks;

	private String withdrawSubject;

	private String withdrawType;

	//bi-directional many-to-one association to CustomInfo
	private CustomInfo customInfo;

	public AaWithdrawBill() {
	}

	public int getAaWithdrawId() {
		return this.aaWithdrawId;
	}

	public void setAaWithdrawId(int aaWithdrawId) {
		this.aaWithdrawId = aaWithdrawId;
	}

	public String getAaNumber() {
		return this.aaNumber;
	}

	public void setAaNumber(String aaNumber) {
		this.aaNumber = aaNumber;
	}

	public String getAaWithdrawSn() {
		return this.aaWithdrawSn;
	}

	public void setAaWithdrawSn(String aaWithdrawSn) {
		this.aaWithdrawSn = aaWithdrawSn;
	}

	public String getAdditional1() {
		return this.additional1;
	}

	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}

	public String getAdditional2() {
		return this.additional2;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
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

	public String getIsOtherMoney() {
		return this.isOtherMoney;
	}

	public void setIsOtherMoney(String isOtherMoney) {
		this.isOtherMoney = isOtherMoney;
	}

	public String getMoneyType() {
		return this.moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayChannel() {
		return this.payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayEmail() {
		return this.payEmail;
	}

	public void setPayEmail(String payEmail) {
		this.payEmail = payEmail;
	}

	public String getPayName() {
		return this.payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayPhone() {
		return this.payPhone;
	}

	public void setPayPhone(String payPhone) {
		this.payPhone = payPhone;
	}

	public String getShowEmailOrNot() {
		return this.showEmailOrNot;
	}

	public void setShowEmailOrNot(String showEmailOrNot) {
		this.showEmailOrNot = showEmailOrNot;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWithdrawRemarks() {
		return this.withdrawRemarks;
	}

	public void setWithdrawRemarks(String withdrawRemarks) {
		this.withdrawRemarks = withdrawRemarks;
	}

	public String getWithdrawSubject() {
		return this.withdrawSubject;
	}

	public void setWithdrawSubject(String withdrawSubject) {
		this.withdrawSubject = withdrawSubject;
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
package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the pay_for_me database table.
 * 
 */
public class PayForMe implements Serializable {
	private static final long serialVersionUID = 1L;
	private int payForMeId;

	private String additiaonal1;

	private String confirmCode;

	private Date createTime;

	private BigDecimal fee;

	private String feeCustId;

	private String feeState;

	private int goodsId;

	private String isOtherMoney;

	private String moneyType;

	private BigDecimal payAmount;

	private String payEmail;

	private String payForMeRemark;

	private String payForMeSn;

	private String payForMeSubject;

	private String payForMeType;

	private String payName;

	private String payState;

	//bi-directional many-to-one association to CustomInfo
	private CustomInfo customInfo;

	public PayForMe() {
	}

	public int getPayForMeId() {
		return this.payForMeId;
	}

	public void setPayForMeId(int payForMeId) {
		this.payForMeId = payForMeId;
	}

	public String getAdditiaonal1() {
		return this.additiaonal1;
	}

	public void setAdditiaonal1(String additiaonal1) {
		this.additiaonal1 = additiaonal1;
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

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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

	public String getPayEmail() {
		return this.payEmail;
	}

	public void setPayEmail(String payEmail) {
		this.payEmail = payEmail;
	}

	public String getPayForMeRemark() {
		return this.payForMeRemark;
	}

	public void setPayForMeRemark(String payForMeRemark) {
		this.payForMeRemark = payForMeRemark;
	}

	public String getPayForMeSn() {
		return this.payForMeSn;
	}

	public void setPayForMeSn(String payForMeSn) {
		this.payForMeSn = payForMeSn;
	}

	public String getPayForMeSubject() {
		return this.payForMeSubject;
	}

	public void setPayForMeSubject(String payForMeSubject) {
		this.payForMeSubject = payForMeSubject;
	}

	public String getPayForMeType() {
		return this.payForMeType;
	}

	public void setPayForMeType(String payForMeType) {
		this.payForMeType = payForMeType;
	}

	public String getPayName() {
		return this.payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayState() {
		return this.payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public CustomInfo getCustomInfo() {
		return this.customInfo;
	}

	public void setCustomInfo(CustomInfo customInfo) {
		this.customInfo = customInfo;
	}

}
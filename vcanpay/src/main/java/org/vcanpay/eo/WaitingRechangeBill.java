package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the waiting_rechange_bill database table.
 * 
 */
public class WaitingRechangeBill implements Serializable {
	private static final long serialVersionUID = 1L;

	private int custWaitRecId;

	private String additional;

	private BigDecimal amount;

	private String bankCardNo;

	private String bankType;

	private String displayYourEmail;

	private String isOtherMoney;

	private String note;

	private Date operateTime;

	private int peopleNum;

	private String recipientAmount;

	private String recipientEmail;

	private String sn;

	private String state;

	private String subject;

	//bi-directional many-to-one association to CustomInfo
	private CustomInfo customInfo;

	public WaitingRechangeBill() {
	}

	public int getCustWaitRecId() {
		return this.custWaitRecId;
	}

	public void setCustWaitRecId(int custWaitRecId) {
		this.custWaitRecId = custWaitRecId;
	}

	public String getAdditional() {
		return this.additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankCardNo() {
		return this.bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankType() {
		return this.bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getDisplayYourEmail() {
		return this.displayYourEmail;
	}

	public void setDisplayYourEmail(String displayYourEmail) {
		this.displayYourEmail = displayYourEmail;
	}

	public String getIsOtherMoney() {
		return this.isOtherMoney;
	}

	public void setIsOtherMoney(String isOtherMoney) {
		this.isOtherMoney = isOtherMoney;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public int getPeopleNum() {
		return this.peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getRecipientAmount() {
		return this.recipientAmount;
	}

	public void setRecipientAmount(String recipientAmount) {
		this.recipientAmount = recipientAmount;
	}

	public String getRecipientEmail() {
		return this.recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public CustomInfo getCustomInfo() {
		return this.customInfo;
	}

	public void setCustomInfo(CustomInfo customInfo) {
		this.customInfo = customInfo;
	}

}
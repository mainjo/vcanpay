package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the recharge_bill database table.
 * 
 */
public class RechargeBill implements Serializable {
	private static final long serialVersionUID = 1L;

	private int custRechargeId;

	private String additional1;

	private String additional2;

	private BigDecimal amount;

	private String bankAddrNo;

	private String bankArea;

	private String bankCardName;

	private String bankCardNo;

	private String bankName;

	private String bankProvince;

	private String bankResultCode;

	private String bankResultNote;

	private Date bankTime;

	private String custBankCode;

	private String custBankName;

	private String custBankType;

	private String note;

	private Date operateTime;

	private String rechargeSn;

	private int rechargeType;
	
	private String remittanceNo;

	private String resultNote;

	private String settleDateTime;

	private String state;

	private String workDateTime;

	//bi-directional many-to-one association to CustomInfo
	
	private CustomInfo customInfo;

	public RechargeBill() {
	}


	public int getCustRechargeId() {
		return this.custRechargeId;
	}

	public void setCustRechargeId(int custRechargeId) {
		this.custRechargeId = custRechargeId;
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

	public String getBankArea() {
		return this.bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
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

	public String getBankProvince() {
		return this.bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
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

	public Date getBankTime() {
		return this.bankTime;
	}

	public void setBankTime(Date bankTime) {
		this.bankTime = bankTime;
	}

	public String getCustBankCode() {
		return this.custBankCode;
	}

	public void setCustBankCode(String custBankCode) {
		this.custBankCode = custBankCode;
	}

	public String getCustBankName() {
		return this.custBankName;
	}

	public void setCustBankName(String custBankName) {
		this.custBankName = custBankName;
	}

	public String getCustBankType() {
		return this.custBankType;
	}

	public void setCustBankType(String custBankType) {
		this.custBankType = custBankType;
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

	public String getRechargeSn() {
		return this.rechargeSn;
	}

	public void setRechargeSn(String rechargeSn) {
		this.rechargeSn = rechargeSn;
	}

	public int getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(int rechargeType) {
		this.rechargeType = rechargeType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getResultNote() {
		return this.resultNote;
	}

	public void setResultNote(String resultNote) {
		this.resultNote = resultNote;
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

	public String getWorkDateTime() {
		return this.workDateTime;
	}

	public void setWorkDateTime(String workDateTime) {
		this.workDateTime = workDateTime;
	}
	public CustomInfo getCustomInfo() {
		return this.customInfo;
	}

	public void setCustomInfo(CustomInfo customInfo) {
		this.customInfo = customInfo;
	}
	
	public String getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(String remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

}
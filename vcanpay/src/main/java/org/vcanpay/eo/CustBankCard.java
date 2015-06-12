package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the cust_bank_card database table.
 * 
 */
public class CustBankCard implements Serializable {
	private static final long serialVersionUID = 1L;

	private int customBankId;

	private String accountState;

	private String accountType;

	private String bankAddrNo;

	private String bankArea;

	private String bankCardNo;

	private String bankName;

	private String bankProvince;

	private String bindingFlag;

	private String branchName;

	private String cardPicFileName;

	private Date createTime;

	private String haveMobileCheck;

	private String haveMoneyCheck;

	private String mobielCheckNo;

	private String mobilePhone;

	private BigDecimal remitMoney;
	
	private BigDecimal inputMoney;


	private String userAddress;

	private Date userBirth;

	private String userName;

	private String userSex;
	private int remitPeopleId;
/*     */   private int checkErrTimes;

	//bi-directional many-to-one association to CustomInfo
	private CustomInfo customInfo;

	public CustBankCard() {
	}

	public int getCustomBankId() {
		return this.customBankId;
	}

	public void setCustomBankId(int customBankId) {
		this.customBankId = customBankId;
	}

	public String getAccountState() {
		return this.accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getBindingFlag() {
		return this.bindingFlag;
	}

	public void setBindingFlag(String bindingFlag) {
		this.bindingFlag = bindingFlag;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCardPicFileName() {
		return this.cardPicFileName;
	}

	public void setCardPicFileName(String cardPicFileName) {
		this.cardPicFileName = cardPicFileName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHaveMobileCheck() {
		return this.haveMobileCheck;
	}

	public void setHaveMobileCheck(String haveMobileCheck) {
		this.haveMobileCheck = haveMobileCheck;
	}

	public String getHaveMoneyCheck() {
		return this.haveMoneyCheck;
	}

	public void setHaveMoneyCheck(String haveMoneyCheck) {
		this.haveMoneyCheck = haveMoneyCheck;
	}

	public String getMobielCheckNo() {
		return this.mobielCheckNo;
	}

	public void setMobielCheckNo(String mobielCheckNo) {
		this.mobielCheckNo = mobielCheckNo;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public BigDecimal getRemitMoney() {
		return this.remitMoney;
	}

	public void setRemitMoney(BigDecimal remitMoney) {
		this.remitMoney = remitMoney;
	}

	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Date getUserBirth() {
		return this.userBirth;
	}

	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public CustomInfo getCustomInfo() {
		return this.customInfo;
	}

	public void setCustomInfo(CustomInfo customInfo) {
		this.customInfo = customInfo;
	}
	
	public BigDecimal getInputMoney() {
		return inputMoney;
	}

	public void setInputMoney(BigDecimal inputMoney) {
		this.inputMoney = inputMoney;
	}
	public int getRemitPeopleId() {
		return remitPeopleId;
	}

	public void setRemitPeopleId(int remitPeopleId) {
		this.remitPeopleId = remitPeopleId;
	}

/*     */   public int getCheckErrTimes() {
/* 274 */     return this.checkErrTimes;
/*     */   }
/*     */ 
/*     */   public void setCheckErrTimes(int checkErrTimes) {
/* 278 */     this.checkErrTimes = checkErrTimes;
/*     */   }
/*     */ }

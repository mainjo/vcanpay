package org.vcanpay.eo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CustomInfo
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private int customId;
    private String address;
    private String addressToo;
    private String agreePolicy;
    private String agreeUser;

    private Date birthDate;
    private String city;
    private String country;

    private Date createTime;

    private Date loginTime;
    private char credType;
    private String customGrade;
    private String customName;
    private int customScore;
    private String custType;
    private String district;
    private String email;
    private String emailConfirm;
    private String firstName;
    private String ICardId;
    private String ifCertificate;
    private String ifTrustName;
    private String knowFrom;
    private String lastName;
    private String loginPwd;
    private String mobiePhone;
    private String mobileConfirm;
    private String mobileOfCountry;
    private String nationality;
    private String pin;
    private String postCode;
    private String province;
    private String region;
    private String sex;
    private String state;
    private String vcpBankAccount;
    private int loginErrTimes;
    private String transBank;
    private String transBranch;
    private String teleOfHome;
    private String teleOfOffice;
    private String tradePwd;

    private List<AaWithdrawBill> aaWithdrawBills;

    private List<CustBankCard> custBankCards;

    private List<LoanBill> loanBills;

    private List<PayForMe> payForMes;

    private List<RechargeBill> rechargeBills;

    private List<WaitingRechangeBill> waitingRechangeBills;

    private List<WithdrawBill> withdrawBills;


    public CustomerAccount customAccounts;

    public CustomerAccount getCustomAccounts() {
        return customAccounts;
    }

    public void setCustomAccounts(CustomerAccount customAccounts) {
        this.customAccounts = customAccounts;
    }

    public int getCustomId() {
        return this.customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgreePolicy() {
        return this.agreePolicy;
    }

    public void setAgreePolicy(String agreePolicy) {
        this.agreePolicy = agreePolicy;
    }

    public String getAgreeUser() {
        return this.agreeUser;
    }

    public void setAgreeUser(String agreeUser) {
        this.agreeUser = agreeUser;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public char getCredType() {
        return credType;
    }

    public void setCredType(char credType) {
        this.credType = credType;
    }

    public String getCustomGrade() {
        return this.customGrade;
    }

    public void setCustomGrade(String customGrade) {
        this.customGrade = customGrade;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public int getCustomScore() {
        return this.customScore;
    }

    public void setCustomScore(int customScore) {
        this.customScore = customScore;
    }

    public String getCustType() {
        return this.custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailConfirm() {
        return this.emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getICardId() {
        return this.ICardId;
    }

    public void setICardId(String ICardId) {
        this.ICardId = ICardId;
    }

    public String getIfCertificate() {
        return this.ifCertificate;
    }

    public void setIfCertificate(String ifCertificate) {
        this.ifCertificate = ifCertificate;
    }

    public String getIfTrustName() {
        return this.ifTrustName;
    }

    public void setIfTrustName(String ifTrustName) {
        this.ifTrustName = ifTrustName;
    }

    public String getKnowFrom() {
        return this.knowFrom;
    }

    public void setKnowFrom(String knowFrom) {
        this.knowFrom = knowFrom;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoginPwd() {
        return this.loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getMobiePhone() {
        return this.mobiePhone;
    }

    public void setMobiePhone(String mobiePhone) {
        this.mobiePhone = mobiePhone;
    }

    public String getMobileConfirm() {
        return this.mobileConfirm;
    }

    public void setMobileConfirm(String mobileConfirm) {
        this.mobileConfirm = mobileConfirm;
    }

    public String getMobileOfCountry() {
        return this.mobileOfCountry;
    }

    public void setMobileOfCountry(String mobileOfCountry) {
        this.mobileOfCountry = mobileOfCountry;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTeleOfHome() {
        return this.teleOfHome;
    }

    public void setTeleOfHome(String teleOfHome) {
        this.teleOfHome = teleOfHome;
    }

    public String getTeleOfOffice() {
        return this.teleOfOffice;
    }

    public void setTeleOfOffice(String teleOfOffice) {
        this.teleOfOffice = teleOfOffice;
    }

    public String getTradePwd() {
        return this.tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public List<AaWithdrawBill> getAaWithdrawBills() {
        return this.aaWithdrawBills;
    }

    public void setAaWithdrawBills(List<AaWithdrawBill> aaWithdrawBills) {
        this.aaWithdrawBills = aaWithdrawBills;
    }

    public AaWithdrawBill addAaWithdrawBill(AaWithdrawBill aaWithdrawBill) {
        getAaWithdrawBills().add(aaWithdrawBill);
        aaWithdrawBill.setCustomInfo(this);

        return aaWithdrawBill;
    }

    public AaWithdrawBill removeAaWithdrawBill(AaWithdrawBill aaWithdrawBill) {
        getAaWithdrawBills().remove(aaWithdrawBill);
        aaWithdrawBill.setCustomInfo(null);

        return aaWithdrawBill;
    }

    public List<CustBankCard> getCustBankCards() {
        return this.custBankCards;
    }

    public void setCustBankCards(List<CustBankCard> custBankCards) {
        this.custBankCards = custBankCards;
    }

    public CustBankCard addCustBankCard(CustBankCard custBankCard) {
        getCustBankCards().add(custBankCard);
        custBankCard.setCustomInfo(this);

        return custBankCard;
    }

    public CustBankCard removeCustBankCard(CustBankCard custBankCard) {
        getCustBankCards().remove(custBankCard);
        custBankCard.setCustomInfo(null);

        return custBankCard;
    }

    public List<LoanBill> getLoanBills() {
        return this.loanBills;
    }

    public void setLoanBills(List<LoanBill> loanBills) {
        this.loanBills = loanBills;
    }

    public LoanBill addLoanBill(LoanBill loanBill) {
        getLoanBills().add(loanBill);
        loanBill.setCustomInfo(this);

        return loanBill;
    }

    public LoanBill removeLoanBill(LoanBill loanBill) {
        getLoanBills().remove(loanBill);
        loanBill.setCustomInfo(null);

        return loanBill;
    }

    public List<PayForMe> getPayForMes() {
        return this.payForMes;
    }

    public void setPayForMes(List<PayForMe> payForMes) {
        this.payForMes = payForMes;
    }

    public PayForMe addPayForMe(PayForMe payForMe) {
        getPayForMes().add(payForMe);
        payForMe.setCustomInfo(this);

        return payForMe;
    }

    public PayForMe removePayForMe(PayForMe payForMe) {
        getPayForMes().remove(payForMe);
        payForMe.setCustomInfo(null);

        return payForMe;
    }

    public List<RechargeBill> getRechargeBills() {
        return this.rechargeBills;
    }

    public void setRechargeBills(List<RechargeBill> rechargeBills) {
        this.rechargeBills = rechargeBills;
    }

    public RechargeBill addRechargeBill(RechargeBill rechargeBill) {
        getRechargeBills().add(rechargeBill);
        rechargeBill.setCustomInfo(this);

        return rechargeBill;
    }

    public RechargeBill removeRechargeBill(RechargeBill rechargeBill) {
        getRechargeBills().remove(rechargeBill);
        rechargeBill.setCustomInfo(null);

        return rechargeBill;
    }

    public List<WaitingRechangeBill> getWaitingRechangeBills() {
        return this.waitingRechangeBills;
    }

    public void setWaitingRechangeBills(List<WaitingRechangeBill> waitingRechangeBills) {
        this.waitingRechangeBills = waitingRechangeBills;
    }

    public WaitingRechangeBill addWaitingRechangeBill(WaitingRechangeBill waitingRechangeBill) {
        getWaitingRechangeBills().add(waitingRechangeBill);
        waitingRechangeBill.setCustomInfo(this);

        return waitingRechangeBill;
    }

    public WaitingRechangeBill removeWaitingRechangeBill(WaitingRechangeBill waitingRechangeBill) {
        getWaitingRechangeBills().remove(waitingRechangeBill);
        waitingRechangeBill.setCustomInfo(null);

        return waitingRechangeBill;
    }

    public List<WithdrawBill> getWithdrawBills() {
        return this.withdrawBills;
    }

    public void setWithdrawBills(List<WithdrawBill> withdrawBills) {
        this.withdrawBills = withdrawBills;
    }

    public WithdrawBill addWithdrawBill(WithdrawBill withdrawBill) {
        getWithdrawBills().add(withdrawBill);
        withdrawBill.setCustomInfo(this);

        return withdrawBill;
    }

    public WithdrawBill removeWithdrawBill(WithdrawBill withdrawBill) {
        getWithdrawBills().remove(withdrawBill);
        withdrawBill.setCustomInfo(null);

        return withdrawBill;
    }

    public String getAddressToo() {
        return this.addressToo;
    }

    public void setAddressToo(String addressToo) {
        this.addressToo = addressToo;
    }

    public String getTransBank() {
        return this.transBank;
    }

    public void setTransBank(String transBank) {
        this.transBank = transBank;
    }

    public String getTransBranch() {
        return this.transBranch;
    }

    public void setTransBranch(String transBranch) {
        this.transBranch = transBranch;
    }

    public String getVcpBankAccount() {
        return this.vcpBankAccount;
    }

    public void setVcpBankAccount(String vcpBankAccount) {
        this.vcpBankAccount = vcpBankAccount;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public int getLoginErrTimes() {
        return this.loginErrTimes;
    }

    public void setLoginErrTimes(int loginErrTimes) {
        this.loginErrTimes = loginErrTimes;
    }

}


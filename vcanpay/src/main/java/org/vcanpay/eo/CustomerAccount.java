package org.vcanpay.eo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by patrick wai on 2015/7/17.
 */
public class CustomerAccount implements Serializable{
    private int amountId;
    private double amount;
    private double cashAmount;
    private Date createTime;
    private double lastTermAmount;
    private double uncashAmount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAmountId() {
        return amountId;
    }

    public void setAmountId(int amountId) {
        this.amountId = amountId;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getLastTermAmount() {
        return lastTermAmount;
    }

    public void setLastTermAmount(double lastTermAmount) {
        this.lastTermAmount = lastTermAmount;
    }

    public double getUncashAmount() {
        return uncashAmount;
    }

    public void setUncashAmount(double uncashAmount) {
        this.uncashAmount = uncashAmount;
    }
}

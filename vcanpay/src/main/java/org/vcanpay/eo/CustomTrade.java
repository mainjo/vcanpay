package org.vcanpay.eo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CustomTrade implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tradeId;
    private BigInteger auditId;
    private Date auditTime;
    private BigInteger entryId;
    private int fundChange;
    private BigInteger outId;
    private BigInteger tid;
    private int tradeDay;
    private BigDecimal tradeFee;
    private BigDecimal tradeMoney;
    private int tradeMonth;
    private String tradeSn;
    private byte tradeState;
    private int tradeTime;
    private short tradeType;
    private int tradeYear;

    public String getTradeId() {
        return this.tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public BigInteger getAuditId() {
        return this.auditId;
    }

    public void setAuditId(BigInteger auditId) {
        this.auditId = auditId;
    }

    public Date getAuditTime() {
        return this.auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public BigInteger getEntryId() {
        return this.entryId;
    }

    public void setEntryId(BigInteger entryId) {
        this.entryId = entryId;
    }

    public int getFundChange() {
        return this.fundChange;
    }

    public void setFundChange(int fundChange) {
/*  95 */
        this.fundChange = fundChange;
    }

    public BigInteger getOutId() {
/*  99 */
        return this.outId;
    }

    public void setOutId(BigInteger outId) {
/* 103 */
        this.outId = outId;
    }

    public BigInteger getTid() {
/* 107 */
        return this.tid;
    }

    public void setTid(BigInteger tid) {
/* 111 */
        this.tid = tid;
    }

    public int getTradeDay() {
/* 115 */
        return this.tradeDay;
    }

    public void setTradeDay(int tradeDay) {
/* 119 */
        this.tradeDay = tradeDay;
    }

    public BigDecimal getTradeFee() {
/* 123 */
        return this.tradeFee;
    }

    public void setTradeFee(BigDecimal tradeFee) {
/* 127 */
        this.tradeFee = tradeFee;
    }

    public BigDecimal getTradeMoney() {
/* 131 */
        return this.tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
/* 135 */
        this.tradeMoney = tradeMoney;
    }

    public int getTradeMonth() {
/* 139 */
        return this.tradeMonth;
    }

    public void setTradeMonth(int tradeMonth) {
/* 143 */
        this.tradeMonth = tradeMonth;
    }

    public String getTradeSn() {
/* 147 */
        return this.tradeSn;
    }

    public void setTradeSn(String tradeSn) {
/* 151 */
        this.tradeSn = tradeSn;
    }

    public byte getTradeState() {
/* 155 */
        return this.tradeState;
    }

    public void setTradeState(byte tradeState) {
/* 159 */
        this.tradeState = tradeState;
    }

    public int getTradeTime() {
/* 163 */
        return this.tradeTime;
    }

    public void setTradeTime(int tradeTime) {
/* 167 */
        this.tradeTime = tradeTime;
    }

    public short getTradeType() {
/* 171 */
        return this.tradeType;
    }

    public void setTradeType(short tradeType) {
/* 175 */
        this.tradeType = tradeType;
    }

    public int getTradeYear() {
/* 179 */
        return this.tradeYear;
    }

    public void setTradeYear(int tradeYear) {
/* 183 */
        this.tradeYear = tradeYear;
    }
}
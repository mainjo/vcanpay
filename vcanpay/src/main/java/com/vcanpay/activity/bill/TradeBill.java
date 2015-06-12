package com.vcanpay.activity.bill;

import java.util.Date;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class TradeBill {
    private Integer id;
    private Date date;
    private Double amount;
    private TradeType type;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TradeType getType() {
        return type;
    }

    public void setType(TradeType type) {
        this.type = type;
    }
}

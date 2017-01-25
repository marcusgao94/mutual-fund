package com.team11.mutualfund.model;

import java.math.BigDecimal;

public class Transition {
    private int fundId;
    private BigDecimal price;

    public int getFundId() {
        return fundId;
    }
    public void setFundId(int id) {
        fundId = id;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

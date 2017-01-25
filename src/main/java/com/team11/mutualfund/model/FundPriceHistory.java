package com.team11.mutualfund.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FundPriceHistory implements Serializable {

    @EmbeddedId
    private FundDate fundDate;

    @Column(scale = 2)
    private double price;

    @MapsId("fundId")
    @OneToOne
    @JoinColumn
    private Fund fund;

    public FundDate getFundDate() {
        return fundDate;
    }

    public void setFundDate(FundDate fundDate) {
        this.fundDate = fundDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }
}

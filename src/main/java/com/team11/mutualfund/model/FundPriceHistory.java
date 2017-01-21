package com.team11.mutualfund.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by marcusgao on 17/1/21.
 */

@Entity
public class FundPriceHistory implements Serializable {

    @EmbeddedId
    private FundDate fundDate;

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

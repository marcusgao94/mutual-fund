package com.team11.mutualfund.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcusgao on 17/1/21.
 */

@Entity
public class Position implements Serializable {

    @EmbeddedId
    private CustomerFund customerFund;

    @MapsId("customerId")
    @ManyToOne
    private Customer customer;

    @MapsId("fundId")
    @ManyToOne
    private Fund fund;

    private double share;

    public CustomerFund getCustomerFund() {
        return customerFund;
    }

    public void setCustomerFund(CustomerFund customerFund) {
        this.customerFund = customerFund;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }
}

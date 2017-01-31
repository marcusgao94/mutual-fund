package com.team11.mutualfund.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Position implements Serializable {

    @EmbeddedId
    private CustomerFund customerFund;

    @Column(scale = 3)
    private double shares;

    @MapsId("customerId")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @MapsId("fundId")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", referencedColumnName = "id")
    private Fund fund;



    @Column(scale = 3)
    private double pendingShareDecrease = 0.0;

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

    public double getShares() {
        return shares;
    }

    public void setShares(double shares) {
        this.shares = shares;
    }

    public double getPendingShareDecrease() {
        return pendingShareDecrease;
    }

    public void setPendingShareDecrease(double pendingShareDecrease) {
        this.pendingShareDecrease = pendingShareDecrease;
    }
}

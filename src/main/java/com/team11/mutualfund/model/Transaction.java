package com.team11.mutualfund.model;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Fund fund;

    @Column(nullable = false)
    private Date exectuteDate;

    private int type = 0;

    private double shares = 0.0;

    private double amount = 0.0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getExectuteDate() {
        return exectuteDate;
    }

    public void setExectuteDate(Date exectuteDate) {
        this.exectuteDate = exectuteDate;
    }

    public double getShares() {
        return shares;
    }

    public void setShares(double shares) {
        this.shares = shares;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

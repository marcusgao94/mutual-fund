package com.team11.mutualfund.model;

import com.team11.mutualfund.utils.TransactionType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Fund fund;

    @Column(nullable = true)
    private LocalDate executeDate = null;

    @Enumerated
    private TransactionType type;

    @Column(scale = 3)
    private Double shares = 0.0;

    @Column(scale = 2)
    private Double amount = 0.0;

    public Transaction() {}
    public Transaction(Customer customer, Fund fund, TransactionType type, Double shares, Double amount) {
        setCustomer(customer);
        setFund(fund);
        setType(type);
        setShares(shares);
        setAmount(amount);
    }
    /*
    public Transaction(TransactionForm tf) {
        setId(tf.getId());
        setExectuteDate(tf.getExecuteDate());
        setType(tf.getType());
        setShares(tf.getShares());
        setAmount(tf.getAmount());
    }
    */


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

    public LocalDate getExectuteDate(){
        return executeDate;
    }

    public void setExectuteDate(LocalDate executeDate) {
        this.executeDate = executeDate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getShares() {
        return shares;
    }

    public void setShares(Double shares) {
        this.shares = shares;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

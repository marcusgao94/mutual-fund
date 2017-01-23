package com.team11.mutualfund.utils;

import com.team11.mutualfund.model.Transaction;

import java.util.Date;

public class TransactionForm {

    private Long id;

    private Long customerId;

    private Long fundId;

    private Date executeDate;

    private TransactionType type;

    private Double shares = 0.0;

    private Double amount = 0.0;

    public TransactionForm() {}
    public TransactionForm(Transaction t) {
        setId(t.getId());
        setCustomerId(t.getCustomer().getId());
        if (t.getFund() != null)
            setFundId(t.getFund().getId());
        setExecuteDate(t.getExectuteDate());
        setType(t.getType());
        setShares(t.getShares());
        setAmount(t.getAmount());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
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

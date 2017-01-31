package com.team11.mutualfund.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DepositCheckForm {

    @NotNull(message = "customer username cannot be null")
    private String userName;

    @NotNull(message = "amount cannot be null")
    @Min(1)
    private Double amount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

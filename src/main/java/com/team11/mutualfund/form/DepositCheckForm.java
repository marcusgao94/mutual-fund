package com.team11.mutualfund.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DepositCheckForm {

    @NotNull(message = "customer id cannot be null")
    private Long customerId;

    @NotNull(message = "amount cannot be null")
    @Min(1)
    private Double amount;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

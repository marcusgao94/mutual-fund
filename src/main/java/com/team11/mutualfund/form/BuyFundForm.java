package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.team11.mutualfund.utils.Constant.TOOLITTLEAMOUNT;

public class BuyFundForm {

    @NotNull
    @Size(min = 1, max = 5)
    private String fundTicker;

    @NotNull
    private Double amount;

    private Double available;

    public Errors getValidationError() {
        Errors errors = new DirectFieldBindingResult(this, "buyFundForm");
        if (amount != null && amount < 0.01) {
            errors.rejectValue("amount", "", TOOLITTLEAMOUNT);
        }
        return errors;
    }

    public String getFundTicker() {
        return fundTicker;
    }

    public void setFundTicker(String fundTicker) {
        this.fundTicker = fundTicker;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }
}

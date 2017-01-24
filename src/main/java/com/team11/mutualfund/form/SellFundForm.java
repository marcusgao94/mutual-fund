package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.team11.mutualfund.utils.Constant.TOOLITTLEAMOUNT;
import static com.team11.mutualfund.utils.Constant.TOOLITTLESHARE;

public class SellFundForm {

    @NotNull
    @Size(min = 1, max = 5)
    private String fundTicker;

    @NotNull
    private Double share;

    public Errors getValidationError() {
        Errors errors = new DirectFieldBindingResult(this, "buyFundForm");
        if (share < 0.001) {
            errors.rejectValue("amount", "0", TOOLITTLESHARE);
        }
        return errors;
    }


    public String getFundTicker() {
        return fundTicker;
    }

    public void setFundTicker(String fundTicker) {
        this.fundTicker = fundTicker;
    }

    public Double getShare() {
        return share;
    }

    public void setShare(Double share) {
        this.share = share;
    }
}

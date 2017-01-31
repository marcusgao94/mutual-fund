package com.team11.mutualfund.form;

import com.team11.mutualfund.model.Fund;
import com.team11.mutualfund.model.FundPriceHistory;
import com.team11.mutualfund.utils.TransitionFund;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class TransitionForm {

    private String lastDate;

    @NotNull
    @Size(min = 10, max = 10, message = "new date must be the form MM/dd/yyyy")
    private String newDate;

    private List<TransitionFund> fundList;

    public String sanitize(String s) {
        return s.replace("&", "&qmp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "transitionForm");
        for (TransitionFund tf : fundList) {
            if (tf.getNewPrice() == null) {
                errors.rejectValue("", "", "new price of every fund must be filled");
                break;
            }
        }
        return errors;
    }


    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = sanitize(newDate);
    }

    public List<TransitionFund> getFundList() {
        return fundList;
    }

    public void setFundList(List<TransitionFund> fundList) {
        this.fundList = fundList;
    }
}

package com.team11.mutualfund.form;

import com.team11.mutualfund.model.Fund;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.util.List;

class TransitionFund {
    Fund fund;
    double price;
}

public class TransitionForm {

    @NotNull
    private String date;

    private List<TransitionFund> transitionFundList;

    public String sanitize(String s) {
        return s.replace("&", "&qmp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public void setDate(String d) {
        date = sanitize(d);
    }
    public String getDate() {
        return date;
    }

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "transitionForm");
        if (date == null || date.length() == 0) {
            errors.rejectValue("date", "0", "please enter date MM/dd/yyyy");
        }
        return errors;
    }

    public List<TransitionFund> getTransitionFundList() {
        return transitionFundList;
    }

    public void setTransitionFundList(List<TransitionFund> transitionFundList) {
        this.transitionFundList = transitionFundList;
    }
}

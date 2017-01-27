package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;

public class TransitionForm {

    @NotNull
    private String date;


    private String price;

    public String sanitize(String s) {
        return s.replace("&", "&qmp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public void setDate(String d) {
        date = sanitize(d);
    }
    public String getDate() {
        return date;
    }

    public void setPrice(String p) {
        price = sanitize(p);
    }
    public String getPrice() {
        return price;
    }

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "transitionForm");
        if (date == null || date.length() == 0) {
            errors.rejectValue("date", "0", "please enter date MM/dd/yyyy");
        }
        return errors;
    }
}

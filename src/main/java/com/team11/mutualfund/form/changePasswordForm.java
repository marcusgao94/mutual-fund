package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.Size;

import static com.team11.mutualfund.utils.Constant.EMPTYPASSWORD;
import static com.team11.mutualfund.utils.Constant.INCONSISTENTPASSWORD;

public class changePasswordForm {
    @Size(min = 1, message = EMPTYPASSWORD)
    private String originPassword;

    @Size(min = 1, message = EMPTYPASSWORD)
    private String newPassword;

    private String confirmNewPassword;

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "changePasswordForm");
        if (!newPassword.equals(confirmNewPassword))
            errors.rejectValue("confirmNewPassword", "0", INCONSISTENTPASSWORD);
        return errors;
    }

    public String getOriginPassword() {
        return originPassword;
    }

    public void setOriginPassword(String originPassword) {
        this.originPassword = originPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}

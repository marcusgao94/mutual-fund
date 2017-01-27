package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.team11.mutualfund.utils.Constant.EMPTYPASSWORD;
import static com.team11.mutualfund.utils.Constant.EMPTYUSERNAME;
import static com.team11.mutualfund.utils.Constant.INCONSISTENTPASSWORD;

public class ChangePasswordForm {
    @Size(min = 1, message = EMPTYPASSWORD)
    private String originPassword;

    @Size(min = 1, message = EMPTYPASSWORD)
    private String newPassword;

    private String confirmNewPassword;

    @NotNull
    @Size(min = 1, message = EMPTYUSERNAME)
	private String userName;

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "changePasswordForm");
        if (newPassword != null && !newPassword.equals(confirmNewPassword))
            errors.rejectValue("confirmNewPassword", "", INCONSISTENTPASSWORD);
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

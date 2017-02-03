package com.team11.mutualfund.form;


import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Locale;

import static com.team11.mutualfund.utils.Constant.*;

public class EmployeeRegisterForm {

    @Size(min = 1, message = EMPTYUSERNAME)
    private String userName;

    @Size(min = 1, message = EMPTYPASSWORD)
    private String password;

    private String confirmPassword;

    @NotNull
    @Size(min = 1, message = EMPTYFIRSTNAME)
    private String firstName;

    @NotNull
    @Size(min = 1, message = EMPTYLASTNAME)
    private String lastName;

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "employeeRegisterForm");
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "2", INCONSISTENTPASSWORD);
        }
        return errors;
    }

    public String sanitize(String s) {
        return s.replace("&", "&qmp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = sanitize(userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = sanitize(password);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = sanitize(confirmPassword);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = sanitize(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = sanitize(lastName);
    }

}

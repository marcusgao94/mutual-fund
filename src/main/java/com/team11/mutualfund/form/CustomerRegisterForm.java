package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.Size;

import static com.team11.mutualfund.utils.Constant.EMPTYPASSWORD;
import static com.team11.mutualfund.utils.Constant.EMPTYUSERNAME;
import static com.team11.mutualfund.utils.Constant.INCONSISTENTPASSWORD;
import static com.team11.mutualfund.utils.Constant.CANNOTEMPTY;


public class CustomerRegisterForm {

    @Size(min = 1, message = EMPTYUSERNAME)
    private String userName;

    @Size(min = 1, message = EMPTYPASSWORD)
    private String password;

    private String confirmPassword;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private String firstName;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private String lastName;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private String addr_line1;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private String addr_line2;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private String city;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private String state;
    
    @Size(min = 1, message = CANNOTEMPTY)
    private Integer zip;

    public String sanitize(String s) {
        return s.replace("&", "&qmp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public Errors getValidationErrors() {
        Errors errors = new DirectFieldBindingResult(this, "customerRegisterForm");
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "0", INCONSISTENTPASSWORD);
        }
        return errors;
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

    public String getAddr_line1() {
        return addr_line1;
    }

    public void setAddr_line1(String addr_line1) {
        this.addr_line1 = sanitize(addr_line1);
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = sanitize(city);
    }

    public String getAddr_line2() {
        return addr_line2;
    }

    public void setAddr_line2(String addr_line2) {
        this.addr_line2 = sanitize(addr_line2);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = sanitize(state);
    }
}

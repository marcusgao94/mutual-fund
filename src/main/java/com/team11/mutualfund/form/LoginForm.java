package com.team11.mutualfund.form;

import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import static com.team11.mutualfund.utils.Constant.EMPTYPASSWORD;
import static com.team11.mutualfund.utils.Constant.EMPTYUSERNAME;

public class LoginForm {

    @Size(min = 1, message = EMPTYUSERNAME)
    private String userName;

    @Size(min = 1, message = EMPTYPASSWORD)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

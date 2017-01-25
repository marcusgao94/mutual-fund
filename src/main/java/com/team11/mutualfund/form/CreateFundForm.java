package com.team11.mutualfund.form;

public class CreateFundForm {

    private String fundName;
    private String fundSymbol;


    public String sanitize(String s) {
        return s.replace("&", "&qmp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public String getName() {
        return fundName;
    }

    public void setName(String fundName) {
        this.fundName = sanitize(fundName);
    }

    public String getSymbol() {
        return fundSymbol;
    }

    public void setSymbol(String fundSymbol) {
        this.fundSymbol = sanitize(fundSymbol);
    }

}

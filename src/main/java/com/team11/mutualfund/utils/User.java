package com.team11.mutualfund.utils;

/**
 * Created by marcusgao on 17/1/20.
 */
public class User {
    private String userName;
    private String password;
    private int type; // customer is 0, employee is 1, guest is -1

    public User() {}
    public User(String userName, int type) {
        this.userName = userName;
        this.type = type;
    }

    public boolean isEmployee() {
        return type == 1;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

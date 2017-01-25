package com.team11.mutualfund.utils;


public class User {
    private Long id;
    private String userName;
    private String password;
    private int type; // customer 0, employee 1, guest -1

    public User() {}
    public User(Long id, String userName, int type) {
        setId(id);
        setUserName(userName);
        setType(type);
    }

    // getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

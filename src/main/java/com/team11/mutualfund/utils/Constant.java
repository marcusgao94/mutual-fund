package com.team11.mutualfund.utils;

public class Constant {

    public static final String NOTLOGIN = "You have not logged in as an employee";
    public static final String CUSTOMERNOTLOGIN = "You have not logged in as a customer";
    public static final String CANNOTEMPTY = "Please fill in this field";

    
    public static final String EMPTYUSERNAME = "User name cannot be empty";
    public static final String NOUSERNAME = "User name does not exist";
    public static final String DUPLICATEUSERNAME = "User name already exists";
    public static final String EMPTYFIRSTNAME = "First name cannot be empty";
    public static final String EMPTYLASTNAME = "Last name cannot be empty";

    public static final String EMPTYPASSWORD = "Password cannot be empty";
    public static final String INCONSISTENTPASSWORD = "Password inconsistent";
    public static final String WRONGPASSWORD = "Password is wrong";

    public static final String NOCUSTOMER = "Customer does not exit";

    public static final String NOENOUGHCASH = "Cash not enough";
    public static final String TOOLITTLEAMOUNT = "Amount must >= 0.01";

    public static final String NOENOUGHSHARE = "Share not enough";
    public static final String TOOLITTLESHARE = "Share must >= 0.001";

    public static final String NOPOSITION = "This customer does not have this fund";

    public static final String NOFUND = "Fund does not exist";
    public static final String NOFUNDPRICE = "Fund has no price";
    public static final String DUPLICATEFUNDTICKER = "Fund ticker already exists";
    public static final String NOFUNDPRICEHISTORY = "Price of this fund on this date is not set";
    public static final String DUPLICATEFUNDPRICEHISTORY = "Price of this fund on this date " +
            "has set";
    public static final String NEWFUNDCREATED = "New fund has been created, please refresh";
    public static final String WRONGTRANSITIONDAY = "Transition day must after last transition day";

    public static final String REQUESTCHECKSUCCESS = "Your request has been submitted successfully! "
    		+ "Please wait for the next transition day!";

    public static final String SETTRANSITIONDAY = "New transition day has been set successfully!";

    public static final String EMPTYADDR = "Address line cannot be empty";
    public static final String EMPTYCITY = "City cannot be empty";
    public static final String EMPTYSTATE = "State cannot be empty";
    public static final String EMPTYZIP = "Zip cannot be empty";

    //public static final String DEPOSITCHECK = "Deposit check successfully";

}

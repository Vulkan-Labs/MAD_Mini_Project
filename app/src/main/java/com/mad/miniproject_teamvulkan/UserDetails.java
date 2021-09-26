package com.mad.miniproject_teamvulkan;

public class UserDetails {

    private String fullName;
    private String phoneNo;
    private String emailAddress;
    private String accountType;

    public UserDetails() {
    }

    public UserDetails(String fullName, String phoneNo, String emailAddress, String accountType) {
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        this.accountType = accountType;
    }




    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

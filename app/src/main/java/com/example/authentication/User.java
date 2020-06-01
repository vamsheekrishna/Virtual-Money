package com.example.authentication;

public class User {
    static private User user;
    private User() {

    }
    public static User getInstance() {
        if(user == null) {
            user = new User();
        }
        return user;
    }

    String userID;
    String fullName;
    String token;
    String walletID;
    String accountNO;
    String amount;
    String DisplayName;

    public String getWalletID() {
        return walletID;
    }

    public void setWalletID(String walletID) {
        this.walletID = walletID;
    }

    public String getAccountNO() {
        return accountNO;
    }

    public void setAccountNumber(String accountNO) {
        this.accountNO = accountNO;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


}

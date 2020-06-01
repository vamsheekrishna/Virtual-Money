package com.example.server.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POJOWalletBalance extends POJOBaseClass{
    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data{
        @SerializedName("wallet_id")
        @Expose
        String walletID;

        @SerializedName("user_id")
        @Expose
        String userID;

        @SerializedName("account_no")
        @Expose
        String accountNumber;

        @SerializedName("amt")
        @Expose
        String amount;

        @SerializedName("display_name")
        @Expose
        String displayName;

        public String getWalletID() {
            return walletID;
        }

        public void setWalletID(String walletID) {
            this.walletID = walletID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}

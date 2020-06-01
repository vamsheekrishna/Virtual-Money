package com.example.server.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POJOLogin extends POJOBaseClass{

    @SerializedName("data")
    @Expose
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {
        @SerializedName("user_id")
        @Expose
        String userID;

        @SerializedName("full_name")
        @Expose
        String fullName;

        @SerializedName("account_no")
        @Expose
        String accountNO;

        @SerializedName("token")
        @Expose
        String token;

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

        public String getAccountNO() {
            return accountNO;
        }

        public void setAccountNO(String accountNO) {
            this.accountNO = accountNO;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

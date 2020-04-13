package com.example.baseclasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.monet_transfer.R;

public class UserData {
    String mName;
    String mMobile;
    String mPassword;
    String mBalance;
    static UserData userData;
    public static UserData getInstance() {
        if(userData == null) {
            userData = new UserData();

        }
        return userData;
    }

    public String getFirstName(BaseActivity context) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        String name = sharedPref.getString("name", null);
        if(null == name) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name", "Virtual Wallet BOS");
            editor.apply();
            name = sharedPref.getString("name", null);
        }
        return name;
    }

    public String getMobileNumber(BaseActivity context) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        String mobile = sharedPref.getString("mobile", null);
        if(null == mobile) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("mobile", "7416226233");
            editor.apply();
            mobile = sharedPref.getString("mobile", null);
        }
        return mobile;
    }

    public String getPassword(BaseActivity context) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        String password = sharedPref.getString("password", null);
        if(null == password) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("password", "Password@123");
            editor.apply();
            password = sharedPref.getString("password", null);
        }
        return password;
    }

    public String getTPIN(Activity context) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        String tpin = sharedPref.getString("tpin", null);
        if(null == tpin) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("tpin", "1234");
            editor.apply();
            tpin = sharedPref.getString("tpin", null);
        }
        return tpin;
    }

    public String getBalance(BaseActivity context) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        long balance = sharedPref.getLong("balance", -1);
        if(-1 == balance) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("balance", 50000);
            editor.apply();
            balance = sharedPref.getLong("balance", -1);
        }
        return balance+"";
    }

    public void updateBalance(Long value, Activity context) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        long balance = sharedPref.getLong("balance", -1);
        balance  = balance - value;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("balance", balance);
        editor.apply();
    }
}

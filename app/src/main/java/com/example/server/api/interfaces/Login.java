package com.example.server.api.interfaces;

import com.example.monet_transfer.BuildConfig;
import com.example.server.api.pojo.POJOWalletBalance;
import com.example.server.api.pojo.POJOLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Login {
    @POST(BuildConfig.LOGIN)
    @FormUrlEncoded
    Call<POJOLogin> login(@Field("username") String mobileNo,
                          @Field("password") String password);

    @POST(BuildConfig.WALLET_BALANCE)
    @FormUrlEncoded
    Call<POJOWalletBalance> walletBalance(@Header("Authorization") String token, @Field("account_no") String accountNO, @Field("user_id") String userID);
}

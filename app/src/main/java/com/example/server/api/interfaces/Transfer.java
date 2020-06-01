package com.example.server.api.interfaces;

import com.example.monet_transfer.BuildConfig;
import com.example.server.api.pojo.POJOTransferRequest;
import com.example.server.api.pojo.POJOVerification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Transfer {
    @POST(BuildConfig.TRANSFER_VERIFICATION)
    @FormUrlEncoded
    Call<POJOVerification> verification(@Header("Authorization") String token, @Field("trasfer_ref_no") int trasferRefNo,
                                        @Field("receiver_card_id") String receiverCardID,@Field("trans_amt") String transAmt,
                                        @Field("tpin") String tpin, @Field("user_id") String userID);

    @POST(BuildConfig.TRANSFER_REQUEST)
    @FormUrlEncoded
    Call<POJOTransferRequest> transferRequest(@Header("Authorization") String token, @Field("sender_card_id") String accountNO,
                                              @Field("trans_amt") int transAmt, @Field("description") String description,
                                              @Field("receiver_card_id") String receiverCardID, @Field("user_id") String userID);
}

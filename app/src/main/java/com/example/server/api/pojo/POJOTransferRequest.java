package com.example.server.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POJOTransferRequest extends POJOBaseClass{
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("sender_card_id")
        @Expose
        String senderCardID;

        @SerializedName("receiver_card_id")
        @Expose
        String receiverCardID;

        @SerializedName("trasfer_ref_no")
        @Expose
        int transferRefNumber;

        @SerializedName("trans_amt")
        @Expose
        String transAmt;

        public String getTransAmt() {
            return transAmt;
        }

        public void setTransAmt(String transAmt) {
            this.transAmt = transAmt;
        }

        public String getSenderCardID() {
            return senderCardID;
        }

        public void setSenderCardID(String senderCardID) {
            this.senderCardID = senderCardID;
        }

        public String getReceiverCardID() {
            return receiverCardID;
        }

        public void setReceiverCardID(String receiverCardID) {
            this.receiverCardID = receiverCardID;
        }

        public int getTransferRefNumber() {
            return transferRefNumber;
        }

        public void setTransferRefNumber(int transferRefNumber) {
            this.transferRefNumber = transferRefNumber;
        }
    }
}

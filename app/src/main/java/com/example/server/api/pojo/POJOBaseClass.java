package com.example.server.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class POJOBaseClass {
    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("code")
    @Expose
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

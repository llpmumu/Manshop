package com.manshop.android.model.msg;

/**
 * Created by Lin on 2018/1/17.
 */

public class LoginRespMsg<T> extends BaseRespMsg {

    private String token ;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    private T data ;

    public String getTocken() {
        return token;
    }

    public void setTocken(String tocken) {
        this.token = tocken;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
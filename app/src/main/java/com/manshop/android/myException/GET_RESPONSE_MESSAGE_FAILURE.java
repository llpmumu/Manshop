package com.manshop.android.myException;

/**
 * Created by Lin on 2018/1/17.
 */

public class GET_RESPONSE_MESSAGE_FAILURE extends Exception {
    private static final long serialVersionUID = -6547904276907101599L;
    public GET_RESPONSE_MESSAGE_FAILURE() {
        super();
    }

    public GET_RESPONSE_MESSAGE_FAILURE(String message) {
        super(message);
    }

    public GET_RESPONSE_MESSAGE_FAILURE(String message, Throwable cause) {
        super(message, cause);
    }

    public GET_RESPONSE_MESSAGE_FAILURE(Throwable cause) {
        super(cause);
    }
}

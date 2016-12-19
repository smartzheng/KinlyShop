package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by thinkpad on 2016/11/23.
 */
public class RegisterErrorInfo implements IResponse {

    /**
     * error : 该用户名已经被注册过了
     * error_code : 1532
     * response : error
     */

    private String error;
    private String error_code;
    private String response;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

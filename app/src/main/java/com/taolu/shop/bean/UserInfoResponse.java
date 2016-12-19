package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by thinkpad on 2016/11/23.
 */
public class UserInfoResponse implements IResponse {

    /**
     * response : register
     * userInfo : {"userid":"164520"}
     */

    public String response;
    /**
     * userid : 164520
     */

    public UserInfoBean userInfo;


    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "response='" + response + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }

    public static class UserInfoBean {
        public String userid;

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "userid='" + userid + '\'' +
                    '}';
        }
    }
}

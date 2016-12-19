package com.itheima.rbcqt.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by xiongmc on 2016/11/21.
 */
public class LoginResponse implements IResponse {

    /**
     * response : login
     * userInfo : {"userid":"20428"}
     */

    public String response;
    /**
     * userid : 20428
     */

    public UserInfoBean userInfo;

    public static class UserInfoBean {
        public String userid;
    }
}

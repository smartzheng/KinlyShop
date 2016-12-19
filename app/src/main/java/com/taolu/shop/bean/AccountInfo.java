package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by m1858 on 2016/11/25.
 */
public class AccountInfo implements IResponse {


    /**
     * response : userInfo
     * userInfo : {"bonus":221,"favoritesCount":2,"level":"普通会员","orderCount":10,"userid":"85842"}
     */

    private String response;
    private UserInfoBean userInfo;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * bonus : 221
         * favoritesCount : 2
         * level : 普通会员
         * orderCount : 10
         * userid : 85842
         */

        private int bonus;
        private int favoritesCount;
        private String level;
        private int orderCount;
        private String userid;

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        public int getFavoritesCount() {
            return favoritesCount;
        }

        public void setFavoritesCount(int favoritesCount) {
            this.favoritesCount = favoritesCount;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
